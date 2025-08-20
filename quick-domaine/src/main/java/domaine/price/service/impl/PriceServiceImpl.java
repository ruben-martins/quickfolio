package domaine.price.service.impl;

import domaine.account.exception.AccountException;
import domaine.account.model.Account;
import domaine.account.persistance.AccountPersistance;
import domaine.annotations.DomainService;
import domaine.portfolio.model.Portfolio;
import domaine.price.service.PriceService;
import domaine.price.model.Price;
import domaine.price.model.PricedPortfolio;
import domaine.price.persitance.PricePersistance;

import java.util.List;
import java.util.UUID;

@DomainService
public class PriceServiceImpl implements PriceService {

    private final PricePersistance pricePersistence;
    private final AccountPersistance accountPersistence;

    public PriceServiceImpl(PricePersistance pricePersistence, AccountPersistance accountPersistence) {
        this.pricePersistence = pricePersistence;
        this.accountPersistence = accountPersistence;
    }

    @Override
    public void updatePrice(Price price) {
        System.out.println("Updating price for Symbol: " + price.getSymbol() + " to " + price.getPrice());
        pricePersistence.save(price);
    }

    @Override
    public List<PricedPortfolio> getPricedPortfolio(UUID accountId) {
        System.out.println("Retrieving priced portfolio for account ID: " + accountId);

        Account account = accountPersistence.findById(accountId)
                .orElseThrow(() -> new AccountException("Account not found for ID: " + accountId));

        return account.portfolios()
                .parallelStream()
                .map(this::mapPricedPortfolio)
                .toList();

    }

    private PricedPortfolio mapPricedPortfolio(Portfolio portfolio) {
        PricedPortfolio pricedPortfolio = new PricedPortfolio(portfolio.name());
        portfolio.positions().forEach(position -> pricedPortfolio.addPosition(position, getPriceBySymbol(position.getSymbol())));
        return pricedPortfolio;
    }

    private Double getPriceBySymbol(String symbol) {
        return pricePersistence.findPriceBySymbol(symbol);
    }

}
