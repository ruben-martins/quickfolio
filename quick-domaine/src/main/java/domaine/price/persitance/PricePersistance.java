package domaine.price.persitance;

import domaine.price.model.Price;

public interface PricePersistance {
    void save(Price price);

    Double findPriceBySymbol(String symbol);
}
