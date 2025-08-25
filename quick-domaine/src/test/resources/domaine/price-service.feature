Feature: Price Service

  Scenario: Retrieve priced portfolio for an account with multiple positions
    Given an account with id "123e4567-e89b-12d3-a456-426614174000" and a portfolio with positions:
      | symbol | size  |
      | AAPL   | -10.0 |
      | TSLA   | 5.0   |
      | AMZN   | 2.0   |
    And the prices for symbols are:
      | symbol | price  |
      | AAPL   | 150.0  |
      | TSLA   | 335.16 |
      | AMZN   | 231.49 |
    When I request the priced portfolio for the account
    Then the priced portfolio should contain the following positions:
      | symbol | size  | price  |
      | AAPL   | -10.0 | 150.0  |
      | TSLA   | 5.0   | 335.16 |
      | AMZN   | 2.0   | 231.49 |
    And the total value of the priced portfolio should be 3638.78