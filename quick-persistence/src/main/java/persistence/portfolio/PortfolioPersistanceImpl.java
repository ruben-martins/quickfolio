package persistence.portfolio;

import domaine.portfolio.model.Portfolio;
import domaine.portfolio.persistence.PortfolioPersistance;
import persistence.account.AccountRepository;
import persistence.configuration.PersistanceService;

import java.util.Optional;
import java.util.UUID;

@PersistanceService
public class PortfolioPersistanceImpl implements PortfolioPersistance {

    private final PortfolioRepository portfolioRepository;
    private final AccountRepository accountRepository;

    public PortfolioPersistanceImpl(PortfolioRepository portfolioRepository,
                                    AccountRepository accountRepository) {
        this.portfolioRepository = portfolioRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Portfolio save(Portfolio portfolio) {
        PortfolioEntity entity = PortfolioMapper.toEntity(portfolio);
        entity.setAccount(accountRepository.getReferenceById(portfolio.accountId()));
        entity.getPositions().forEach(position -> position.setPortfolio(entity));
        return PortfolioMapper.toModel(portfolioRepository.save(entity));
    }

    @Override
    public Optional<Portfolio> findByAccountIdAndPortfolionName(UUID accountId, String name) {
        PortfolioEntity entity = portfolioRepository.findByNameAndAccount_id(name, accountId);
        return Optional.ofNullable(PortfolioMapper.toModel(entity));
    }

    @Override
    public void deleteByAccountIdAndPortfolioName(UUID accountId, String name) {
        PortfolioEntity entity = portfolioRepository.findByNameAndAccount_id(name, accountId);
        if (entity != null) {
            portfolioRepository.delete(entity);
        }
    }

    @Override
    public Optional<Portfolio> findPortfolioById(UUID portfolioId) {
        return portfolioRepository.findById(portfolioId).map(PortfolioMapper::toModel);
    }
}
