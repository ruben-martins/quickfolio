package domaine.portfolio.messages;

public enum PortfolioMessages {

    ERROR_PORTFOLIO_NAME_ALREADY_EXISTS("A portfolio with name '%s' already exists.");

    private final String message;

    PortfolioMessages(String message) {
        this.message = message;
    }

    public String getMessage(Object... arguments) {
        return String.format(this.message, arguments);
    }

}
