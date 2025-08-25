Feature: Account Service

  Scenario: Create a new account successfully
    Given no account exists with email "user@email.com"
    When I create an account with username "user" and email "user@email.com"
    Then the account should be created with username "user" and email "user@email.com"

  Scenario: Fail to create account when email already exists
    Given an account exists with email "user@email.com"
    When I create an account with username "user" and email "user@email.com"
    Then an AccountException with message containing "already exists" should be thrown

  Scenario: Get account by email successfully
    Given an account exists with email "user@email.com"
    When I get the account by email "user@email.com"
    Then the account should be returned with email "user@email.com"

  Scenario: Update account successfully
    Given an account exists with id "11111111-1111-1111-1111-111111111111"
    When I update the account with id "11111111-1111-1111-1111-111111111111"
    Then the account should be updated

  Scenario: Fail to update account with invalid id
    Given no account exists with id "22222222-2222-2222-2222-222222222222"
    When I update the account with id "22222222-2222-2222-2222-222222222222"
    Then an AccountException with message containing "Invalid account ID" should be thrown

  Scenario: Delete account successfully
    Given an account exists with email "user@email.com"
    When I delete the account with email "user@email.com"
    Then the account should be deleted

  Scenario: Fail to delete account when email not found
    Given no account exists with email "notfound@email.com"
    When I delete the account with email "notfound@email.com"
    Then an AccountException with message containing "not found" should be thrown