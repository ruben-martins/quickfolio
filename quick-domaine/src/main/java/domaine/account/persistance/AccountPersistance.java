package domaine.account.persistance;

import domaine.account.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountPersistance {
    Account save(Account account);

    Optional<Account> findAccountByEmail(String email);

    void deleteById(UUID id);

    Optional<Account> findById(UUID id);
}