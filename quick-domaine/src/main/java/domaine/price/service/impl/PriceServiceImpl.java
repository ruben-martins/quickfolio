package domaine.price.service.impl;

import domaine.account.exception.AccountException;
import domaine.account.model.Account;
import domaine.account.persistance.AccountPersistance;
import domaine.annotations.DomainService;
import domaine.portfolio.model.Portfolio;
import domaine.portfolio.model.Position;
import domaine.price.model.Price;
import domaine.price.model.PricedPortfolio;
import domaine.price.model.PricedPosition;
import domaine.price.persitance.PricePersistance;
import domaine.price.service.PriceService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Map<String, PricedPosition> pricedPositions = portfolio
                .positions()
                .stream()
                .collect(Collectors.toMap(Position::getSymbol, this::getPricedPosition));
        return new PricedPortfolio(portfolio.name(), pricedPositions);
    }

    private PricedPosition getPricedPosition(Position p) {
        return new PricedPosition(p, getPriceBySymbol(p.getSymbol()));
    }

    private Double getPriceBySymbol(String symbol) {
        return pricePersistence.findPriceBySymbol(symbol);
    }

}
