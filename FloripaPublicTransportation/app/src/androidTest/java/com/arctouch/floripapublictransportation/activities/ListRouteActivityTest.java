package com.arctouch.floripapublictransportation.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.entities.Route;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


/**
 * Created by GabrielPacheco on 22/01/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ListRouteActivityTest {

    @Rule
    public ActivityTestRule<ListRouteActivity> mActivityRule = new ActivityTestRule<>(
            ListRouteActivity.class);

    @Test
    public void testSearchGovernadorResultBeiraMarNorte() {
        String mStringToBetyped = "governador";

        // Type text and then press the button.
        onView(withId(R.id.editTextSearch))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard());

        onView(withId(R.id.buttonSearch)).perform(click());

        onData(is(instanceOf(Route.class)))
                .inAdapterView(withId(R.id.listViewRoute))
                .atPosition(0)
                .perform(click());
    }

    @Test
    public void testSearchGovernadorResultUfscSemiDireto() {
        String mStringToBetyped = "governador";

        // Type text and then press the button.
        onView(withId(R.id.editTextSearch))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard());

        onView(withId(R.id.buttonSearch)).perform(click());

        onData(is(instanceOf(Route.class)))
                .inAdapterView(withId(R.id.listViewRoute))
                .atPosition(3)
                .perform(click());

        onView(withId(R.id.buttonBack)).perform(click());
    }

    @Test
    public void testMaps(){
        onView(withId(R.id.buttonShowMaps)).perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.buttonSearchMaps)).perform(click());

        onView(withId(R.id.buttonBackMaps)).perform(click());
    }

}
