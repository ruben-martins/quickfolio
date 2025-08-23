package domaine.account.service;

import domaine.account.exception.AccountException;
import domaine.account.model.Account;
import domaine.account.model.CreateAccountRequest;

import java.util.Optional;

public interface AccountService {

    Account createAccount(CreateAccountRequest createAccountRequest);

    Optional<Account> getAccountByEmail(String name);

    Account updateAccount(Account account);

    void deleteAccount(String email) throws AccountException;

}