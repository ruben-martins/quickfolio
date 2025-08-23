package domaine.portfolio.model;

import java.util.UUID;

public class Position {

    private UUID id;
    private String symbol;
    private Double size;

    public Position() {
        this.size = 0.0;
    }

    public Position(UUID id, String symbol, Double size) {
        this.id = id;
        this.symbol = symbol;
        this.size = size;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
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
