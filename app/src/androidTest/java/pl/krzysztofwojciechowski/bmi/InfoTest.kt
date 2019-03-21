package pl.krzysztofwojciechowski.bmi


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InfoTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun infoTest() {
        val editMass = onView(withId(R.id.editMass))
        editMass.perform(scrollTo(), replaceText("60"), closeSoftKeyboard())
        editMass.perform(pressImeActionButton())

        val editHeight = onView(withId(R.id.editHeight))
        editHeight.perform(scrollTo(), replaceText("190"), closeSoftKeyboard())
        editHeight.perform(pressImeActionButton())

        val fab = onView(withId(R.id.fab))
        fab.perform(scrollTo(), click())

        val number = onView(withId(R.id.bmi_info_number))
        number.check(matches(withText("16.62")))

        val imageView = onView(withId(R.id.bmi_info_image))
        imageView.check(matches(isDisplayed()))

        val longText = onView(withId(R.id.bmi_info_longtext))
        longText.check(matches(withText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")))
    }
}
