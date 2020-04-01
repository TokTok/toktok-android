package im.tox.toktok.app;

import androidx.annotation.NonNull;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import im.tox.toktok.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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
    public void sendMessage() {
        // Enter the chat with Lorem Ipsum.
        onView(allOf(withId(R.id.home_item_name), withText("Lorem Ipsum")))
                .perform(click());

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

    @Test
    public void enterAndLeaveMessageWindow() {
        // We're in the main view, so we have a tab list, but no message input.
        onView(withId(R.id.message_input)).check(doesNotExist());
        onView(withId(R.id.home_tabs)).check(matches(anything()));

        // Enter the chat with Lorem Ipsum.
        onView(allOf(withId(R.id.home_item_name), withText("Lorem Ipsum")))
                .perform(click());

        // Check that the message input field exists and the tabs are gone.
        onView(withId(R.id.message_input)).check(matches(anything()));
        onView(withId(R.id.home_tabs)).check(doesNotExist());

        // Back to the main view.
        pressBack();

        // Message input should be gone again, and tabs should be back.
        onView(withId(R.id.message_input)).check(doesNotExist());
        onView(withId(R.id.home_tabs)).check(matches(anything()));
    }

    @Test
    public void rejectVideoCall() {
        // Go to the Friends tab.
        onView(withText("Friends")).perform(click());

        // Click on one of the friends.
        onView(withText("Bart Simpson")).perform(click());

        // Click "call".
        onView(allOf(withText("Call"), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .perform(click());

        // Swipe the decline button to the left.
        onView(withId(R.id.call_decline))
                .perform(swipeLeft());
    }

}
