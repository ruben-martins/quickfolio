package domaine.price.model;

import domaine.portfolio.model.Position;

import java.util.HashMap;
import java.util.Map;

public class PricedPortfolio {

    private final String name;
    private Double totalValue;
    private final Map<String, PricedPosition> positionBySymbol = new HashMap<>();


    public PricedPortfolio(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Double getTotalValue() {
        if (totalValue == null) {
            this.totalValue = calculateTotalValue();
        }
        return totalValue;
    }

    public Map<String, PricedPosition> getPositionBySymbol() {
        return positionBySymbol;
    }

    public void addPosition(Position position, Double price) {
        PricedPosition pricedPosition = new PricedPosition(position, price);
        positionBySymbol.put(position.getSymbol(), pricedPosition);
    }

    public PricedPosition getPricedPosition(String symbol) {
        return positionBySymbol.get(symbol);
    }

    public Double calculateTotalValue() {
        return positionBySymbol.values().stream()
                .mapToDouble(p -> p.getSize() * p.getPrice())
                .sum();
    }


}
