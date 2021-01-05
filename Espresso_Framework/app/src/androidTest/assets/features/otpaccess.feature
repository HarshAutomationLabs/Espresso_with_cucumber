Feature: Get data from message app

  @otp-feature
  Scenario: Navigate to message app
    Given I am on login screen
    When I navigate to messages app
    And Find the otp in message app
    And Navigate back to espresso app
    Then Enter the otp in login app


