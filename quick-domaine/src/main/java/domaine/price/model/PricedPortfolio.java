package domaine.price.model;

import domaine.portfolio.model.Position;

import java.util.HashMap;
import java.util.Map;

public class PricedPortfolio {

    private final String name;
    private final Map<String, PricedPosition> pricedPositionBySymbol = new HashMap<>();

    public PricedPortfolio(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addPosition(Position position, Double price) {
        PricedPosition pricedPosition = new PricedPosition(position, price);
        pricedPositionBySymbol.put(position.getSymbol(), pricedPosition);
    }

    public PricedPosition getPricedPosition(String symbol) {
        return pricedPositionBySymbol.get(symbol);
    }

    public Double calculateTotalValue() {
        return pricedPositionBySymbol.values().stream()
                .mapToDouble(p -> p.getSize() * p.getPrice())
                .sum();
    }


}
