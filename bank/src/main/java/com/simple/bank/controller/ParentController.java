package com.simple.bank.controller;

import com.simple.bank.config.CustomUserDetails;
import com.simple.bank.domain.entities.Account;
import com.simple.bank.domain.entities.User;
import com.simple.bank.domain.enums.AccountTypes;
import com.simple.bank.dto.AccountDTO;
import com.simple.bank.repository.AccountRepository;
import com.simple.bank.service.AccountService;
import com.simple.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
public class ParentController {
    @Autowired
    private UserService userService;
   @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountDTO accountDTO;

    public final double minBalance = 1000.00;

    @ModelAttribute("currentUserAccounts")
    public List<Account> getCurrentUserAccounts(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return accountService.findAccountsByUserId(customUserDetails.getId());
    }

    @ModelAttribute("accountDTO")
    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/add-account")
    public String processAddCard(@ModelAttribute("accountDTO") AccountDTO accountDTO,
                                 @ModelAttribute("currentUser") CustomUserDetails customUserDetails,
                                 Model model) {
        String username = customUserDetails.getUsername();
        User user = userService.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username=%s was not found", username)));
        Account account;
        if(AccountTypes.SAVINGS_ACCOUNT.getName().equals(accountDTO.getAccountType())) {
            account = new Account(user, accountDTO.getAmount(), activateAccount(accountDTO), accountDTO.getAccountType(),new Date());
        }else{
            account = new Account(user, accountDTO.getAmount(), true, accountDTO.getAccountType(),new Date());
        }
        accountRepository.save(account);
        return "redirect:/";
    }
    public boolean activateAccount(AccountDTO accountDTO){
        return accountDTO.getAmount() >= minBalance ? true : false;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);

        return "redirect:/login";
    }
}
