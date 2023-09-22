package com.lms.project.backend.service;

import java.io.IOException;

import java.util.Optional;
import java.util.UUID;

import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.project.backend.models.Account;
import com.lms.project.backend.repositories.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepo;
    

        @Transactional(rollbackFor = AccountException.class)
        public Account createAccount(Account account) throws AccountException {
            try {
        
                //create Account id. 
                String accountId = UUID.randomUUID().toString().substring(0, 8);

                account.setAccountId(accountId);
                accountRepo.createAccount(account);

                return account;
            } catch (DataIntegrityViolationException ex) {
                String errorMessage = "Email has been taken.";
                throw new AccountException(errorMessage);
            }
        }


        @Transactional(rollbackFor = AccountException.class)
        public Account retrieveAccount(String username) throws AccountException {
        
        
                Optional<Account> retrievedAccount = accountRepo.getAccountByUsername(username);

                return retrievedAccount.get();

  
        }


    public Account loginAccount(String email, String password) throws IOException, AccountNotFoundException {
        Optional<Account> userAccount = accountRepo.getAccountByUsername(email);

        if (userAccount.isPresent()) {
            Account loggedInAccount = userAccount.get();
            System.out.printf(">>>String Password is >>>" + password);
            System.out.printf(">>>loggedInAccountPassword is >>>" + loggedInAccount.getPassword());   
            if (loggedInAccount.getPassword().equals(password)){
                return loggedInAccount;
            }
            else{
              
                throw new AccountNotFoundException("Password is incorrect");
            }

        } else {
          
            throw new AccountNotFoundException("Account not found for email: " + email);
        }
    }



}
