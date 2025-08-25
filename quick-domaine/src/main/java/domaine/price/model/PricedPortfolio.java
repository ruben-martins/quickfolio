package domaine.price.model;

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

    public PricedPosition getPricedPosition(String symbol) {
        return positionBySymbol.get(symbol);
    }

    private Double calculateTotalValue() {
        return positionBySymbol.values().stream()
                .mapToDouble(p -> p.getSize() * p.getPrice())
                .map(Math::abs)
                .sum();
    }

}
