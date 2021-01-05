Feature: Login
    Perform login on email and password are inputted

    @login-feature
    Scenario Outline: Input email and password in wrong format
        Given I am on login screen
        When I input email <row>
        And I input password <row>
        And I press submit button
        Then I should see error on the <row>

    Examples:
        | row |
        |  0  |
        |  1  |

    @login-feature
    Scenario Outline: Input email and password in correct format
        Given I am on login screen
        When I input email <row>
        And I input password <row>
        And I press submit button
        Then I should <row> auth error

    Examples:
        | row |
        |  2  |
        |  3  |
        |  4  |

    @login-feature
    Scenario: Tap login button and show login screen
        Given I am on login screen
        When I tap sign up button
        Then I should see sign up screen