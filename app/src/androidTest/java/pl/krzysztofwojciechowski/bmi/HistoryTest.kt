package pl.krzysztofwojciechowski.bmi


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HistoryTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun historyTest() {
        val editMass = onView(withId(R.id.editMass))
        val editHeight = onView(withId(R.id.editHeight))
        val btnCalculate = onView(withId(R.id.btnCalculate))

        editMass.perform(scrollTo(), replaceText("55"))
        editHeight.perform(scrollTo(), replaceText("180"), pressImeActionButton())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(300)

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(250)

        val changeUnits = onView(
            allOf(
                withId(R.id.title), withText("Change units"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        changeUnits.perform(click())

        editMass.perform(scrollTo(), replaceText("1"))
        editHeight.perform(scrollTo(), replaceText("2"), closeSoftKeyboard())
        btnCalculate.perform(scrollTo(), click())

        editMass.perform(scrollTo(), replaceText("180"))
        editHeight.perform(scrollTo(), replaceText("60"), closeSoftKeyboard())
        btnCalculate.perform(scrollTo(), click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(300)

        overflowMenuButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(250)

        val history = onView(
            allOf(
                withId(R.id.title), withText("History"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        history.perform(click())

        val value1 = onView(
            allOf(
                withId(R.id.bmi_history_value),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmi_history_recyclerview),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        value1.check(matches(withText("35.15")))

        val value2 = onView(
            allOf(
                withId(R.id.bmi_history_value),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmi_history_recyclerview),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        value2.check(matches(withText("16.98")))

        val massAndHeight1 = onView(
            allOf(
                withId(R.id.bmi_history_mass_and_height),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmi_history_recyclerview),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        massAndHeight1.check(matches(withText("180 lb, 60 in")))

        val massAndHeight2 = onView(
            allOf(
                withId(R.id.bmi_history_mass_and_height),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmi_history_recyclerview),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        massAndHeight2.check(matches(withText("55 kg, 180 cm")))

        val date1 = onView(
            allOf(
                withId(R.id.bmi_history_date),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmi_history_recyclerview),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        date1.check(matches(isDisplayed()))

        val date2 = onView(
            allOf(
                withId(R.id.bmi_history_date),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmi_history_recyclerview),
                        1
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        date2.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
