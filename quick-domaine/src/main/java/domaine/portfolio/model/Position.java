package domaine.portfolio.model;

public class Position {

    private final String symbol;
    private Double size;

    public Position() {
        this.symbol = null;
        this.size = 0.0;
    }

    public Position(String symbol, Double size) {
        this.symbol = symbol;
        this.size = size;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public void addSize(Double size) {
        if (size != null) {
            this.size += size;
        }
    }

}
