package domaine.price.model;

import domaine.portfolio.model.Position;

import java.util.Map;

public class PricedPortfolio {

    private final String name;
    private final Double totalValue;
    private final Map<String, PricedPosition> positionBySymbol;


    public PricedPortfolio(String name, Map<String, PricedPosition> positionBySymbol) {
        this.name = name;
        this.positionBySymbol = positionBySymbol;
        this.totalValue = calculateTotalValue();
    }

    public String getName() {
        return name;
    }

    public Double getTotalValue() {
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
