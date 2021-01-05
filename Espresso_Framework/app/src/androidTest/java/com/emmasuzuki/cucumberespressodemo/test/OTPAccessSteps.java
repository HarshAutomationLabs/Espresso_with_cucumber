package com.emmasuzuki.cucumberespressodemo.test;

import android.app.Activity;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import com.emmasuzuki.cucumberespressodemo.LoginActivity;
import org.junit.Rule;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

public class OTPAccessSteps {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);
    private Activity activity;

    UiDevice mDevice;
    String MESSAGE_APP = "Messages";
    String ESPRESSO_APP = "EspressoSpoon";
    String OTPMessage;

    @Before("@otp-feature")
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @After("@otp-feature")
    public void tearDown() {
        activityTestRule.finishActivity();
    }

    @Given("^I am on login screen")
    public void I_am_on_login_screen() {
        assertNotNull(activity);
    }

    @When("^I navigate to messages app$")
    public void i_navigate_to_messages_app() throws Throwable {
        this.mDevice.pressHome();
        UiObject messageApp = this.mDevice.findObject(new UiSelector().text(this.MESSAGE_APP));
        messageApp.clickAndWaitForNewWindow();
    }

    @And("^Find the otp in message app$")
    public void find_the_otp_in_message_app() throws Throwable{
        UiObject object = this.mDevice.findObject(new UiSelector().packageName("com.google.android.apps.messaging"));
        UiObject child = object.getChild(new UiSelector().className("android.widget.TextView").index(1));
        this.OTPMessage = child.getText();
    }

    @And("^Navigate back to espresso app")
    public void Navigate_back_to_espresso_app() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        this.mDevice.pressRecentApps();
        UiObject appBackground = this.mDevice.findObject(new UiSelector().description(ESPRESSO_APP));
        appBackground.click();
        appBackground.waitForExists(5000);
    }

    @Then("^Enter the otp in login app$")
    public void enter_the_otp_in_login_app() throws Throwable {
        Log.d("OTP", this.OTPMessage);
    }
}
