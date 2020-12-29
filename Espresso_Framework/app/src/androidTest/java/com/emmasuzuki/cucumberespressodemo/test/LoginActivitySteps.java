package com.emmasuzuki.cucumberespressodemo.test;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.emmasuzuki.cucumberespressodemo.LoginActivity;
import com.emmasuzuki.cucumberespressodemo.R;
import org.junit.Rule;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import Utility.ExcelReader;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static Utility.CustomMatcher.hasErrorText;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.not;

public class LoginActivitySteps {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);
    private Activity activity;
    List<Map<String, String>> testData;

    @Before("@login-feature")
    public void setup() throws IOException {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();

        InputStream dataStream = getInstrumentation().getContext().getResources()
                .getAssets().open("TestData.xls");
        ExcelReader externalData = new ExcelReader();
        testData = externalData.getData(dataStream, "Sheet1");
    }

    @After("@login-feature")
    public void tearDown() {
        activityTestRule.finishActivity();
    }

    @Given("^I am on login screen")
    public void I_am_on_login_screen() {
        assertNotNull(activity);
    }

    @When("^I input email (\\d+)$")
    public void I_input_email(final int row) {
        String email = testData.get(row).get("Email");
        onView(withId(R.id.email)).perform(typeText(email));
    }

    @When("^I input password (\\d+)$")
    public void I_input_password(final int row) {
        String password = testData.get(row).get("Password");
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard());
    }

    @When("^I press submit button$")
    public void I_press_submit_button() {
        onView(withId(R.id.submit)).perform(click());
    }

    @When("^I tap sign up button$")
    public void I_tap_sign_up_button() {
        onView(withId(R.id.signup)).perform(click());
    }

    @Then("^I should see error on the (\\d+)$")
    public void I_should_see_error_on_the_view(final int row) {
        String viewName = testData.get(row).get("View");
        int viewId = (viewName.equals("email")) ? R.id.email : R.id.password;
        int messageId = (viewName.equals("email")) ? R.string.msg_email_error : R.string.msg_password_error;

        onView(withId(viewId)).check(matches(hasErrorText(activity.getString(messageId))));
    }

    @Then("^I should (\\d+) auth error$")
    public void I_should_see_auth_error(final int row) {
        String errorFlag = testData.get(row).get("See");
        boolean shouldSeeError = errorFlag.equals("true") ? true : false;
        if (shouldSeeError) {
            onView(withId(R.id.error)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.error)).check(matches(not(isDisplayed())));
        }
    }

    @Then("^I should see sign up screen$")
    public void I_should_see_sign_up_screen() {
        onView(withId(R.id.page_title)).check(matches(withText(R.string.signup)));
    }
}
