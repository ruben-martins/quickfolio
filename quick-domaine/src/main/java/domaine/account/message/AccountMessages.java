package domaine.account.message;

public enum AccountMessages {

    ERROR_EMAIL_ACCOUNT_NOT_FOUND("The account associated with the email %s was not found"),
    ERROR_INVALID_ACCOUNT("Invalid account ID"),
    ERROR_EMAIL_ALREADY_EXISTS("An account with the email %s already exists");

    private final String message;

    AccountMessages(String message) {
        this.message = message;
    }

    public String getMessage(Object... arguments) {
        return String.format(this.message, arguments);
    }

}
