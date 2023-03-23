package com.simple.bank.controller;

import com.simple.bank.config.CustomUserDetails;
import com.simple.bank.domain.entities.Account;
import com.simple.bank.domain.entities.Transaction;
import com.simple.bank.domain.enums.AccountTypes;
import com.simple.bank.domain.enums.TransactionTypes;
import com.simple.bank.dto.DepositDTO;
import com.simple.bank.dto.TransactionDTO;
import com.simple.bank.dto.TransferDTO;
import com.simple.bank.dto.WithdrawalDTO;
import com.simple.bank.repository.AccountRepository;
import com.simple.bank.repository.TransactionRepository;
import com.simple.bank.service.AccountService;
import com.simple.bank.service.TransactionService;
import com.simple.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.smartcardio.Card;
import java.util.Date;
import java.util.List;

@Controller
public class TransactionController {
    @Autowired
    private DepositDTO depositDTO;
    @Autowired
    private TransferDTO transferDTO;
    @Autowired
    private TransactionDTO transactionDTO;
    @Autowired
    private WithdrawalDTO withdrawalDTO;

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;
    public final double minBalance = 1000.00;
    public final double overdraft = -100000.00;
    public double newBalance = 0.00;
    public double newOverdraft = 0.00;

    @ModelAttribute("depositDTO")
    public DepositDTO getDepositDTO() {
        return depositDTO;
    }
    @ModelAttribute("transferDTO")
    public TransferDTO getTransferDTO() {
        return transferDTO;
    }
    @ModelAttribute("withdrawalDTO")
    public WithdrawalDTO getWithdrawalDTO() {
        return withdrawalDTO;
    }
    @ModelAttribute("transactionDTO")
    public TransactionDTO getTransactionDTO() {
        return transactionDTO;
    }

    @GetMapping("/transactions")
    public String getTransactions() {
        return "transactions";
    }

    @ModelAttribute("currentUserAccounts")
    public List<Account> getCurrentUserAccounts(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return accountService.findAccountsByUserId(customUserDetails.getId());
    }



    @GetMapping("/transactions/account/{account_id}")
    public String getAccountTransactions(@PathVariable("account_id") long account_id,
                                      Model model) {
        List<Transaction> accountTransactions = transactionService.findTransactionByAccountId(account_id);
        model.addAttribute("accountTransactions", accountTransactions);
        transactionDTO.setAccount_id(account_id);
        return "transactions-list";
    }
    @GetMapping("/deposit/{account_id}")
    public String getAccountDeposit(@PathVariable("account_id") long account_id,
                                 Model model) {
        depositDTO = new DepositDTO();
        depositDTO.setAccount_id(account_id);
        model.addAttribute("depositDTO",depositDTO);
        return "deposit-form";
    }
    @GetMapping("/withdraw/{account_id}")
    public String getAccountWithdraw(@PathVariable("account_id") long account_id,
                                  Model model) {
        withdrawalDTO = new WithdrawalDTO();
        withdrawalDTO.setAccount_id(account_id);
        model.addAttribute("withdrawalDTO",withdrawalDTO);
        return "withdraw-form";
    }
    @GetMapping("/transfer/{account_id}")
    public String getAccountTransfer(@PathVariable("account_id") long account_id,
                                  Model model) {
        transferDTO = new TransferDTO();
        transferDTO.setAccount_id(account_id);
        model.addAttribute("transferDTO",transferDTO);
        return "transfer-form";
    }

    @PostMapping("/deposit")
    public String depositAmount(@ModelAttribute("depositDTO") DepositDTO depositDTO,Model model) {
        long accountId = depositDTO.getAccount_id();
        double amount = depositDTO.getAmount();
        String transactionType = TransactionTypes.DEPOSIT.getName();
        String comment = depositDTO.getComment();

        Account account = accountService.findAccountById(accountId);

        if (account != null) {
            Transaction transaction = new Transaction(account, amount, comment,transactionType,new Date());
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
            transactionRepository.save(transaction);
            model.addAttribute("depositDTO",new DepositDTO());
            model.addAttribute("successfulTransaction", "successfulTransaction");

        }

        return "deposit-form";
    }
    @PostMapping("/withdraw")
    public String withdrawAmount(@ModelAttribute("withdrawalDTO") WithdrawalDTO withdrawalDTO,Model model) {
        long accountId = withdrawalDTO.getAccount_id();
        double amount = withdrawalDTO.getAmount();
        String transactionType = TransactionTypes.WITHDRAWAL.getName();
        String comment = withdrawalDTO.getComment();

        Account account = accountService.findAccountById(accountId);

        if (account != null) {
            Transaction transaction = new Transaction(account, amount, comment,transactionType,new Date());
            model.addAttribute("withdrawalDTO",new WithdrawalDTO());
            transferOrWithdraw(account,amount,transaction,model);
        }

        return "withdraw-form";
    }
    @PostMapping("/transfer")
    public String transferMoney(@ModelAttribute("transferDTO") TransferDTO transferDTO,Model model) {
        long accountId = transferDTO.getAccount_id();
        double amount = transferDTO.getAmount();
        String transactionType = TransactionTypes.TRANSFER.getName();
        String comment = transferDTO.getComment();
        String account_no = transferDTO.getAccount_no();
        Account account = accountService.findAccountById(accountId);

        if (account != null) {
            Transaction transaction = new Transaction(account,account_no, amount, comment,transactionType,new Date());
            model.addAttribute("transferDTO",new TransferDTO());
            transferOrWithdraw(account,amount,transaction,model);
            }

        return "transfer-form";
    }

    public void transferOrWithdraw(Account account,double amount,Transaction transaction,Model model){
        if(AccountTypes.SAVINGS_ACCOUNT.getName().equals(account.getAccount_type())) {
            newBalance = account.getBalance() - amount;
            if (newBalance >= minBalance) {
                account.setBalance(newBalance);
                accountRepository.save(account);
                transactionRepository.save(transaction);
                model.addAttribute("successfulTransaction", "successfulTransaction");
            }else {
                model.addAttribute("minBalanceError", "minBalanceError");
            }
        }else{
            newBalance = account.getBalance() - amount;
            if (newBalance >= overdraft) {
                account.setBalance(newBalance);
                accountRepository.save(account);
                transactionRepository.save(transaction);
                model.addAttribute("successfulTransaction", "successfulTransaction");
            }else{
                model.addAttribute("maxOverDraftBalanceError", "maxOverDraftBalanceError");
            }
        }
    }

}
