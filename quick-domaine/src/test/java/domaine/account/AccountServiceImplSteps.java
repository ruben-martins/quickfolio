package domaine.account;

import domaine.account.exception.AccountException;
import domaine.account.model.Account;
import domaine.account.model.CreateAccountRequest;
import domaine.account.persistance.AccountPersistance;
import domaine.account.service.impl.AccountServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountServiceImplSteps {

    private AccountPersistance accountPersistance;
    private AccountServiceImpl accountService;
    private Account createdAccount;
    private Exception thrownException;

    @Given("no account exists with email {string}")
    public void no_account_exists_with_email(String email) {
        accountPersistance = Mockito.mock(AccountPersistance.class);
        Mockito.when(accountPersistance.findAccountByEmail(email)).thenReturn(Optional.empty());
        Mockito.when(accountPersistance.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));
        accountService = new AccountServiceImpl(accountPersistance);
    }

    @Given("an account exists with email {string}")
    public void an_account_exists_with_email(String email) {
        accountPersistance = Mockito.mock(AccountPersistance.class);
        Account account = new Account(UUID.randomUUID(), "user", email, new ArrayList<>());
        Mockito.when(accountPersistance.findAccountByEmail(email)).thenReturn(Optional.of(account));
        Mockito.when(accountPersistance.save(Mockito.any())).thenReturn(account);
        accountService = new AccountServiceImpl(accountPersistance);
    }

    @Given("an account exists with id {string}")
    public void an_account_exists_with_id(String id) {
        accountPersistance = Mockito.mock(AccountPersistance.class);
        Account account = new Account(UUID.fromString(id), "user", "user@email.com",  new ArrayList<>());
        Mockito.when(accountPersistance.findById(UUID.fromString(id))).thenReturn(Optional.of(account));
        Mockito.when(accountPersistance.save(Mockito.any())).thenReturn(account);
        accountService = new AccountServiceImpl(accountPersistance);
    }

    @Given("no account exists with id {string}")
    public void no_account_exists_with_id(String id) {
        accountPersistance = Mockito.mock(AccountPersistance.class);
        Mockito.when(accountPersistance.findById(UUID.fromString(id))).thenReturn(Optional.empty());
        accountService = new AccountServiceImpl(accountPersistance);
    }

    @When("I create an account with username {string} and email {string}")
    public void i_create_an_account_with_username_and_email(String username, String email) {
        try {
            createdAccount = accountService.createAccount(new CreateAccountRequest(username, email));
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @When("I get the account by email {string}")
    public void i_get_the_account_by_email(String email) {
        createdAccount = accountService.getAccountByEmail(email).orElse(null);
    }

    @When("I update the account with id {string}")
    public void i_update_the_account_with_id(String id) {
        try {
            Account account = new Account(UUID.fromString(id), "user", "user@email.com",  new ArrayList<>());
            createdAccount = accountService.updateAccount(account);
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @When("I delete the account with email {string}")
    public void i_delete_the_account_with_email(String email) {
        try {
            accountService.deleteAccount(email);
        } catch (Exception e) {
            thrownException = e;
        }
    }

    @Then("the account should be created with username {string} and email {string}")
    public void the_account_should_be_created_with_username_and_email(String username, String email) {
        assertNotNull(createdAccount);
        assertEquals(username, createdAccount.username());
        assertEquals(email, createdAccount.email());
    }

    @Then("an AccountException with message containing {string} should be thrown")
    public void an_account_exception_with_message_should_be_thrown(String messagePart) {
        assertNotNull(thrownException);
        assertInstanceOf(AccountException.class, thrownException);
        assertTrue(thrownException.getMessage().contains(messagePart));
    }

    @Then("the account should be returned with email {string}")
    public void the_account_should_be_returned_with_email(String email) {
        assertNotNull(createdAccount);
        assertEquals(email, createdAccount.email());
    }

    @Then("the account should be updated")
    public void the_account_should_be_updated() {
        assertNotNull(createdAccount);
    }

    @Then("the account should be deleted")
    public void the_account_should_be_deleted() {
        Mockito.verify(accountPersistance).deleteById(Mockito.any());
    }
}