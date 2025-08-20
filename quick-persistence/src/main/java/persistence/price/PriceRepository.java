package persistence.price;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, String> {

    @Query(value = "select p.price from price p where symbol = :symbol", nativeQuery = true)
    Optional<Double> getPriceBySymbol(@Param("symbol") String symbol);

}
