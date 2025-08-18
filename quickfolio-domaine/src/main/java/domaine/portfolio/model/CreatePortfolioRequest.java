package domaine.portfolio.model;

public record CreatePortfolioRequest(String email,
                                     String portfolioName,
                                     String description) {

}
