Feature: Signup screen

    @signup-feature
    Scenario Outline: Sign-up feature with correct data
        Given I am on sign up screen
        When I input "<firstname>" in firstname field
        And I input "<lastname>" in lastname field
        And I input "<email>" in email field
        And I input "<password>" in password field
        And I tap submit button
        Then Verify "<firstname>" "<lastname>" "<email>" texts

        Examples:
            | firstname | lastname | email | password |
            | steven    | rogers | espresso@spoon.com | bananacake |

    @signup-feature
    Scenario Outline: Sign-up feature with incorrect data
        Given I am on sign up screen
        When I input "<firstname>" in firstname field
        And I input "<lastname>" in lastname field
        And I input "<email>" in email field
        And I input "<password>" in password field
        And I tap submit button
        Then I should see error on the "<view>"

        Examples:
            | firstname | lastname | email | password | view |
            || rogers | espresso@spoon.com | bananacake | firstname |
            | steven  | | espresso@spoon.com | bananacake | lastname |
            | steven  | rogers | | bananacake | email |
            | steven  | rogers | espresso@spoon.com | | password |

    @signup-feature
    Scenario: Verify navigation to login screen
        Given I am on sign up screen
        When I click on the login button
        Then user should navigate to login screen