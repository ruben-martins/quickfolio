package domaine.portfolio.service;

import domaine.portfolio.model.CreatePortfolioRequest;
import domaine.portfolio.model.Portfolio;
import domaine.portfolio.model.Position;

import java.util.Optional;
import java.util.UUID;

public interface PortfolioService {

    Portfolio create(CreatePortfolioRequest createPortfolioRequest);

    Optional<Portfolio> update(Portfolio portfolio);

    Optional<Portfolio> findByName(UUID accountId, String portfolioName);

    void delete(UUID accountId, String portfolioName);

    Portfolio addPosition(UUID portfolioId, Position position);
}
