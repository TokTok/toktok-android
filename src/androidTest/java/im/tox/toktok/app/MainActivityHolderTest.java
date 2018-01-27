package im.tox.toktok.app;

import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import im.tox.toktok.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class MainActivityHolderTest {

    private static final String mStringToBeTyped = "Hello world!";

    @NonNull
    @Rule
    public ActivityTestRule<MainActivityHolder> mActivityRule =
            new ActivityTestRule<>(MainActivityHolder.class);

    @Test
    public void changeText_sameActivity() {
        onView(withText("Lorem Ipsum")).perform(click());

        // Type text and then press the send button.
        onView(withId(R.id.message_input))
                .perform(typeText(mStringToBeTyped), closeSoftKeyboard());
        onView(withId(R.id.message_fab)).perform(click());

        // Check that the text is empty.
        onView(withId(R.id.message_input))
                .check(matches(withText("")));

        // Check that a message has been sent.
        onView(allOf(withResourceName("message_item_text"), withText(mStringToBeTyped)))
                .check(matches(anything()));
    }

}
