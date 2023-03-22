package com.simple.bank.validator;

import com.simple.bank.dto.UserDTO;
import com.simple.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserCreateFormValidator implements Validator {
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserDTO.class);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        UserDTO userDTO = (UserDTO) obj;
        validatePasswords(errors, userDTO);
        validateUsername(errors, userDTO);
        validateEmail(errors, userDTO);
    }
    private void validatePasswords(Errors errors, UserDTO userDTO) {
        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm())) {
            errors.rejectValue("password", "msg.PasswordNotMatch");
        }
    }
    private void validateUsername(Errors errors, UserDTO userDTO) {
        if(errors.hasFieldErrors("username"))
            return;

        if (userRepository.findUserByUsername(userDTO.getUsername()).isPresent()) {
            errors.rejectValue("username", "msg.DuplicateUsername");
        }
    }

    private void validateEmail(Errors errors, UserDTO userDTO) {
        //Avoid querying not valid or empty values
        if(errors.hasFieldErrors("email"))
            return;

        if (userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {
            errors.rejectValue("email", "msg.DuplicateEmail");
        }
    }
}
