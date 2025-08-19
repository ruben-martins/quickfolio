package simulation;

import domaine.price.model.Price;
import jakarta.annotation.PostConstruct;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PriceSimulatorService {

    private final KafkaTemplate<String, Price> stockPriceKafkaTemplate;
    private static final List<Price> stockPrices = new ArrayList<>();

    public PriceSimulatorService(KafkaTemplate<String, Price> stockePriceKafkaTemplate) {
        this.stockPriceKafkaTemplate = stockePriceKafkaTemplate;
    }

    @PostConstruct
    public void start() {

        // Initial prices
        stockPrices.add(new Price("AAPL", 230.89));
        stockPrices.add(new Price("GOOG", 204.29));
        stockPrices.add(new Price("AMZN", 231.49));
        stockPrices.add(new Price("MSFT", 517.10));
        stockPrices.add(new Price("TSLA", 335.16));
        stockPrices.add(new Price("META", 767.37));
        stockPrices.add(new Price("NFLX", 1245.09));
        stockPrices.add(new Price("NVDA", 182.01));

        while (true) {
            for (Price stockPrice : stockPrices) {
                Double newPrice = getNewPrice(stockPrice.getPrice());
                stockPrice.setPrice(newPrice);
                System.out.println("Updated price for " + stockPrice.getSymbol() + ": " + newPrice);
                this.stockPriceKafkaTemplate.send("prices", stockPrice);
                timeout(1000);
            }
        }
    }

    private static Double getNewPrice(Double currentPrice) {
        double newPrice = currentPrice * (1 + Math.random() / 100); // Simulate price change
        return Math.round(newPrice * 100.00) / 100.00; // round to 2 decimal places
    }

    private static void timeout(long timeoutInMillis) {
        try {
            Thread.sleep(timeoutInMillis); // Simulate delay
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
