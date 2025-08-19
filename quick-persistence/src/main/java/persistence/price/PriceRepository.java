package persistence.price;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, String> {

    Optional<Double> findPriceBySymbol(String symbol);


}
