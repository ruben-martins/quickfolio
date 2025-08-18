package domaine.account.service.impl;

import domaine.account.exception.AccountException;
import domaine.account.message.AccountMessages;
import domaine.account.model.Account;
import domaine.account.model.CreateAccountRequest;
import domaine.account.persistance.AccountPersistance;
import domaine.account.service.AccountService;
import domaine.annotations.DomainService;

import java.util.Optional;

@DomainService
public class AccountServiceImpl implements AccountService {

    private final AccountPersistance accountPersistance;

    public AccountServiceImpl(AccountPersistance accountPersistance) {
        this.accountPersistance = accountPersistance;
    }

    @Override
    public Account createAccount(CreateAccountRequest createAccountRequest) {
        accountPersistance.findAccountByEmail(createAccountRequest.email())
                .ifPresent(existingAccount -> {
                    throw new AccountException(AccountMessages.ERROR_EMAIL_ALREADY_EXISTS.getMessage(existingAccount.email()));
                });
        return accountPersistance.save(new Account(createAccountRequest.username(), createAccountRequest.email()));
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        return accountPersistance.findAccountByEmail(email);
    }

    @Override
    public Account updateAccount(Account account) {
        if (account.id() == null || accountPersistance.findById(account.id()).isEmpty()) {
            throw new AccountException(AccountMessages.ERROR_INVALID_ACCOUNT.getMessage());
        }
        return accountPersistance.save(account);
    }

    @Override
    public void deleteAccount(String email) throws AccountException {
        Account account = accountPersistance.findAccountByEmail(email)
                .orElseThrow(() -> new AccountException(AccountMessages.ERROR_EMAIL_ACCOUNT_NOT_FOUND.getMessage(email)));
        accountPersistance.deleteById(account.id());
    }
}
