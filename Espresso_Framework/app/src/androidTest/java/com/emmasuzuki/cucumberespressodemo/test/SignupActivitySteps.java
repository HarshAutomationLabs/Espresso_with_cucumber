package com.emmasuzuki.cucumberespressodemo.test;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.emmasuzuki.cucumberespressodemo.R;
import com.emmasuzuki.cucumberespressodemo.SignupActivity;

import org.junit.Rule;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.Matchers.not;

public class SignupActivitySteps {

    @Rule
    private ActivityTestRule<SignupActivity> activityTestRule = new ActivityTestRule<>(SignupActivity.class);

    private SignupActivity activity;

    @Before("@signup-feature")
    public void setUp() {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
    }

    @After("@signup-feature")
    public void tearDown() {
        activityTestRule.finishActivity();
    }

    @Given("^I am on sign up screen$")
    public void I_am_on_sign_up_screen() {
        assertNotNull(activity);
    }

    @When("^I input \"(.*?)\" in firstname field$")
    public void I_input_firstname_in_firstname_field(final String firstname){
        onView(withId(R.id.first_name)).perform(typeText(firstname));
    }

    @And("^I input \"(.*?)\" in lastname field$")
    public void I_input_firstname_in_lastname_field(final String lastname){
        onView(withId(R.id.last_name)).perform(typeText(lastname));
    }

    @And("^I input \"(.*?)\" in email field$")
    public void I_input_email_in_email_field(final String email){
        onView(withId(R.id.email)).perform(typeText(email));
    }

    @And("^I input \"(.*?)\" in password field$")
    public void I_input_password_in_password_field(final String password){
        onView(withId(R.id.password)).perform(typeText(password));
    }

    @And("^I tap submit button$")
    public void I_tap_submit_button() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());
    }

    @And("^I click on the login button$")
    public void I_click_on_the_login_button() {
        onView(withId(R.id.login)).perform(click());
    }

    @Then("^Verify \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" texts$")
    public void verify_texts(final String firstname, String lastname, String email) {
        onView(withId(R.id.first_name)).check(matches(withText(firstname)));
        onView(withId(R.id.last_name)).check(matches(withText(lastname)));
        onView(withId (R.id.email)).check(matches(withText(email)));
    }

    @Then("^I should see error on the \"(.*?)\"$")
    public void I_should_see_error_on_the_editTextView(final String viewName) {
        int viewId;
        switch (viewName) {
            case "firstname": {
                viewId = R.id.first_name;
                break;
            }
            case "lastname": {
                viewId = R.id.last_name;
                break;
            }
            case "email": {
                viewId = R.id.email;
                break;
            }
            default: {
                viewId = R.id.password;
                break;
            }
        }
        int messageId = (viewName.equals("email")) ? R.string.msg_email_error : R.string.msg_password_error;
        onView(withId(viewId)).check(matches(not(hasErrorText(activity.getString(messageId)))));
    }

    @Then("^user should navigate to login screen$")
    public void user_should_navigate_to_login_screen(){
        onView(withId(R.id.page_title)).check(matches(withText("Login")));
    }
}