package pl.krzysztofwojciechowski.bmi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class VisibilityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun visibilityTest() {
        pressBack() // hide keyboard

        val ids = listOf(
            R.id.bmi_main_image,
            R.id.bmi_main_label_mass,
            R.id.bmi_main_label_height,
            R.id.bmi_main_label_value,
            R.id.bmi_main_label_classification,
            R.id.bmi_main_btn_calculate
        )
        ids.forEach { onView(withId(it)).check(matches(isDisplayed())) }
    }

}
