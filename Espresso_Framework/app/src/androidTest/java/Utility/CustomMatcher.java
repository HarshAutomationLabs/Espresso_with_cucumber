package Utility;

import android.view.View;
import android.widget.EditText;

import com.emmasuzuki.cucumberespressodemo.test.LoginActivitySteps;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

public class CustomMatcher {

    public static Matcher<? super View> hasErrorText(String expectedError) {
        return new CustomMatcher.ErrorTextMatcher(expectedError);
    }

    public static class ErrorTextMatcher extends TypeSafeMatcher<View> {

        private final String mExpectedError;

        private ErrorTextMatcher(String expectedError) {
            mExpectedError = expectedError;
        }

        @Override
        public boolean matchesSafely(View view) {
            if (!(view instanceof EditText)) {
                return false;
            }

            EditText editText = (EditText) view;

            return mExpectedError.equals(editText.getError().toString());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("with error: " + mExpectedError);
        }
    }
}
