package domaine.account.model;

import domaine.portfolio.model.Portfolio;

import java.util.List;
import java.util.UUID;

public record Account(UUID id,
                      String username,
                      String email,
                      List<Portfolio> portfolios) {

    public Account(String username, String email) {
        this(null, username, email, List.of());
    }

}
