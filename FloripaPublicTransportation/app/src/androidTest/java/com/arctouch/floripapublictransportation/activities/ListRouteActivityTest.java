package com.arctouch.floripapublictransportation.activities;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.ListView;
import android.widget.TextView;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.adapters.ListViewRoutesAdapter;
import com.arctouch.floripapublictransportation.entities.Route;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringStartsWith.startsWith;


/**
 * Created by GabrielPacheco on 22/01/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ListRouteActivityTest {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<ListRouteActivity> mActivityRule = new ActivityTestRule<>(
            ListRouteActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "governador";
    }

//    @Test
//    public void testTypeTextSearch() {
//        // Type text and then press the button.
//        onView(withId(R.id.editTextSearch))
//                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
//
//        // Check that the text was changed.
//        onView(withId(R.id.editTextSearch)).check(matches(withText(mStringToBetyped)));
//    }

    @Test
    public void testSearchRoutes() {
        // Type text and then press the button.
        onView(withId(R.id.editTextSearch))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard());

        onView(withId(R.id.buttonSearch)).perform(click());

        onData(is(instanceOf(Route.class)))
                .inAdapterView(withId(R.id.listViewRoute))
                .atPosition(0)
                .perform(click());

        // Check that the text was changed.
        //onView(withId(R.id.listViewRoute)).check(matches(withContentDescription("BEIRA MAR NORTE")));
    }



}
