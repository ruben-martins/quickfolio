package domaine.portfolio.model;

public class Position {

    private String key;
    private Double size;

    public Position() {
        this.key = null;
        this.size = 0.0;
    }

    public Position(String key, Double size) {
        this.key = key;
        this.size = size;
    }

    public String getKey() {
        return key;
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
