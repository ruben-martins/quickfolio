package persistence.price;

import domaine.price.model.Price;
import domaine.price.persitance.PricePersistance;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class PricePersistanceImpl implements PricePersistance {

    private final PriceRepository priceRepository;

    public PricePersistanceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public synchronized void save(Price price) {
        PriceEntity priceEntity = priceRepository.getReferenceById(price.getSymbol());
        priceEntity.setPrice(price.getPrice());
        priceRepository.save(priceEntity);
    }

    @Override
    public Double findPriceBySymbol(String symbol) {
        return priceRepository.getPriceBySymbol(symbol).orElse(0d);
    }
}
