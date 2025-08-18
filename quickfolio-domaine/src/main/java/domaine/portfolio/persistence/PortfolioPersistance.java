package domaine.portfolio.persistence;

import domaine.portfolio.model.Portfolio;

import java.util.Optional;
import java.util.UUID;

public interface PortfolioPersistance {
    Portfolio save(Portfolio portfolio);

    Optional<Portfolio> findByAccountIdAndPortfolionName(UUID accountId, String name);

    void deleteByAccountIdAndPortfolioName(UUID accountId, String portfolioName);

    Optional<Portfolio> findPortfolioById(UUID portfolioId);
}
