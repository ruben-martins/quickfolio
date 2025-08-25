package domaine.price;

import domaine.account.model.Account;
import domaine.account.persistance.AccountPersistance;
import domaine.portfolio.model.Portfolio;
import domaine.portfolio.model.Position;
import domaine.price.model.PricedPortfolio;
import domaine.price.persitance.PricePersistance;
import domaine.price.service.impl.PriceServiceImpl;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PriceServiceImplSteps {

    private PriceServiceImpl priceService;
    private PricePersistance pricePersistence;
    private UUID accountId;
    private final List<Position> positions = new ArrayList<>();
    private List<PricedPortfolio> result;

    @Given("an account with id {string} and a portfolio with positions:")
    public void an_account_with_id_and_a_portfolio_with_positions(String id, DataTable dataTable) {
        accountId = UUID.fromString(id);
        AccountPersistance accountPersistence = Mockito.mock(AccountPersistance.class);
        pricePersistence = Mockito.mock(PricePersistance.class);

        for (List<String> row : dataTable.asLists()) {
            if (row.get(0).equals("symbol")) continue; // skip header
            positions.add(new Position(UUID.randomUUID(), row.get(0), Double.parseDouble(row.get(1))));
        }

        Portfolio portfolio = new Portfolio(UUID.randomUUID(), accountId, "Tech", "Tech Stocks", positions);
        Account account = new Account(accountId, "user", "email", List.of(portfolio));

        Mockito.when(accountPersistence.findById(accountId)).thenReturn(Optional.of(account));
        priceService = new PriceServiceImpl(pricePersistence, accountPersistence);
    }

    @And("the prices for symbols are:")
    public void the_prices_for_symbols_are(DataTable dataTable) {
        for (List<String> row : dataTable.asLists()) {

            if (row.get(0).equals("symbol")) continue; // skip header

            String symbol = row.get(0);
            Double price = Double.parseDouble(row.get(1));
            Mockito.when(pricePersistence.findPriceBySymbol(symbol)).thenReturn(price);
        }
    }

    @When("I request the priced portfolio for the account")
    public void i_request_the_priced_portfolio_for_the_account() {
        result = priceService.getPricedPortfolio(accountId);
    }

    @Then("the priced portfolio should contain the following positions:")
    public void the_priced_portfolio_should_contain_the_following_positions(DataTable dataTable) {
        assertNotNull(result);
        assertFalse(result.isEmpty());

        var pricedPortfolio = result.getFirst();
        for (List<String> row : dataTable.asLists()) {
            if (row.get(0).equals("symbol")) continue; // skip header

            var symbol = row.get(0);
            var expectedSize = Double.parseDouble(row.get(1));
            var expectedPrice = Double.parseDouble(row.get(2));
            var pricedPosition = pricedPortfolio.getPricedPosition(symbol);

            assertNotNull(pricedPosition);
            assertEquals(expectedSize, pricedPosition.getSize());
            assertEquals(expectedPrice, pricedPosition.getPrice());
        }
    }

    @And("the total value of the priced portfolio should be {double}")
    public void the_total_value_of_the_priced_portfolio_should_be(double expectedTotalValue) {
        var pricedPortfolio = result.getFirst();
        assertEquals(expectedTotalValue, pricedPortfolio.getTotalValue());
    }
}