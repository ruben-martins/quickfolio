package domaine.price.model;

import domaine.portfolio.model.Position;

public class PricedPosition extends domaine.portfolio.model.Position {

    private Double price;

    public PricedPosition(Position position, Double price) {
        super(position.getSymbol(), position.getSize());
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
