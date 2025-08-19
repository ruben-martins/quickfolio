package domaine.price;

import domaine.price.model.Price;
import domaine.price.model.PricedPortfolio;

import java.util.List;
import java.util.UUID;

public interface PriceService {
    void updatePrice(Price price);

    List<PricedPortfolio> getPricedPortfolio(UUID accountId);
}
