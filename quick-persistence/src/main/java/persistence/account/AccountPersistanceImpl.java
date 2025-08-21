package persistence.account;

import domaine.account.model.Account;
import domaine.account.persistance.AccountPersistance;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AccountPersistanceImpl implements AccountPersistance {

    private final AccountRepository accountRepository;

    public AccountPersistanceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = AccountMapper.toEntity(account);
        return AccountMapper.toModel(accountRepository.save(entity));
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) {
        AccountEntity entity = accountRepository.findByEmail(email);
        return Optional.ofNullable(AccountMapper.toModel(entity));
    }

    @Override
    public void deleteById(UUID id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Optional<Account> findById(UUID id) {
        AccountEntity entity = accountRepository.getReferenceById(id);
        return Optional.ofNullable(AccountMapper.toModel(entity));
    }
}
