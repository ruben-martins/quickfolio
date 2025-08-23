package domaine.portfolio.service.impl;

import domaine.account.exception.AccountException;
import domaine.account.message.AccountMessages;
import domaine.account.model.Account;
import domaine.account.persistance.AccountPersistance;
import domaine.annotations.DomainService;
import domaine.portfolio.PositionVerifier;
import domaine.portfolio.exception.PortfolioException;
import domaine.portfolio.messages.PortfolioMessages;
import domaine.portfolio.model.CreatePortfolioRequest;
import domaine.portfolio.model.Portfolio;
import domaine.portfolio.model.Position;
import domaine.portfolio.persistence.PortfolioPersistance;
import domaine.portfolio.service.PortfolioService;

import java.util.Optional;
import java.util.UUID;

@DomainService
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioPersistance portfolioPersistance;
    private final AccountPersistance accountPersistance;

    public PortfolioServiceImpl(PortfolioPersistance portfolioPersistance, AccountPersistance accountPersistance) {
        this.portfolioPersistance = portfolioPersistance;
        this.accountPersistance = accountPersistance;
    }

    @Override
    public Portfolio create(CreatePortfolioRequest createPortfolioRequest) {

        String email = createPortfolioRequest.email();
        String portfolioName = createPortfolioRequest.portfolioName();

        Account account = accountPersistance.findAccountByEmail(email)
                .orElseThrow(() -> new AccountException(AccountMessages.ERROR_EMAIL_ACCOUNT_NOT_FOUND.getMessage(email)));

        if (portfolioPersistance.findByAccountIdAndPortfolionName(account.id(), portfolioName).isPresent()) {
            throw new PortfolioException(PortfolioMessages.ERROR_PORTFOLIO_NAME_ALREADY_EXISTS.getMessage(portfolioName));
        }

        return portfolioPersistance.save(new Portfolio(account.id(), portfolioName, createPortfolioRequest.description()));
    }

    @Override
    public Optional<Portfolio> update(Portfolio portfolio) {
        if (portfolioPersistance.findByAccountIdAndPortfolionName(portfolio.accountId(), portfolio.name()).isPresent()) {
            return Optional.of(portfolioPersistance.save(portfolio));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Portfolio> findByName(UUID accountId, String portfolioName) {
        return portfolioPersistance.findByAccountIdAndPortfolionName(accountId, portfolioName);
    }

    @Override
    public void delete(UUID accountId, String portfolioName) {
        portfolioPersistance.deleteByAccountIdAndPortfolioName(accountId, portfolioName);
    }

    @Override
    public Portfolio addPosition(UUID portfolioId, Position position) {
        this.checkValidPosition(position);

        Portfolio portfolio = getPortfolioById(portfolioId);
        portfolio.addPosition(position);
        return portfolioPersistance.save(portfolio);
    }

    private Portfolio getPortfolioById(UUID portfolioId) {
        return portfolioPersistance.findPortfolioById(portfolioId)
                .orElseThrow(() -> new PortfolioException("Portfolio not found with id: " + portfolioId));
    }

    private void checkValidPosition(Position position) {
        if (!PositionVerifier.isPositionValid(position)) {
            throw new PortfolioException(PortfolioMessages.ERROR_INVALID_POSITION.getMessage(position.getSize()));
        }

    }

}
