package persistence.price;

import domaine.price.model.Price;
import domaine.price.persitance.PricePersistance;
import org.springframework.stereotype.Component;

@Component
public class PricePersistanceImpl implements PricePersistance {

    private final PriceRepository priceRepository;

    public PricePersistanceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public void save(Price price) {
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setSymbol(priceEntity.getSymbol());
        priceEntity.setPrice(priceEntity.getPrice());
        priceRepository.save(priceEntity);
    }

    @Override
    public Double findPriceBySymbol(String symbol) {
        return priceRepository.findPriceBySymbol(symbol).orElse(0d);
    }
}
