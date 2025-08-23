package persistence.account;

import domaine.account.model.Account;
import domaine.portfolio.model.Portfolio;
import domaine.portfolio.model.Position;
import org.springframework.util.CollectionUtils;
import persistence.portfolio.PortfolioEntity;

import java.util.Collections;
import java.util.List;

public class AccountMapper {

    private AccountMapper() {}

    public static Account toModel(AccountEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Account(entity.getId(), entity.getUsername(), entity.getEmail(), mapPortfolios(entity));
    }

    public static AccountEntity toEntity(Account account) {
        if (account == null) {
            return null;
        }

        AccountEntity entity = new AccountEntity();
        entity.setId(account.id());
        entity.setUsername(account.username());
        entity.setEmail(account.email());
        return entity;
    }

    private static List<Portfolio> mapPortfolios(AccountEntity entity) {
        if (CollectionUtils.isEmpty(entity.getPortfolios())) {
            return Collections.emptyList();
        }

        return entity.getPortfolios()
                .stream()
                .map(e -> new Portfolio(e.getId(), entity.getId(), e.getName(), e.getDescription(), mapPositions(e)))
                .toList();
    }

    private static List<Position> mapPositions(PortfolioEntity e) {
        if (CollectionUtils.isEmpty(e.getPositions())) {
            return Collections.emptyList();
        }
        return e.getPositions().stream()
                .map(p -> new Position(p.getId(), p.getKey(), p.getSize()))
                .toList();
    }

}
