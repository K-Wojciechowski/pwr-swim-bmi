package pl.krzysztofwojciechowski.bmi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class BasicUsageTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun basicUsageTest() {
        val editMass = onView(withId(R.id.bmi_main_edit_mass))
        editMass.perform(scrollTo(), replaceText("65"), closeSoftKeyboard())

        val editHeight = onView(withId(R.id.bmi_main_edit_height))
        editHeight.perform(scrollTo(), replaceText("170"), closeSoftKeyboard())

        val btnCalculate = onView(withId(R.id.bmi_main_btn_calculate))
        btnCalculate.perform(scrollTo(), click())

        val labelValue = onView(withId(R.id.bmi_main_label_value))
        labelValue.check(matches(withText("22.49")))

        val labelClassification = onView(withId(R.id.bmi_main_label_classification))
        labelClassification.check(matches(withText("Normal")))

        editHeight.perform(scrollTo(), replaceText("160"))
        editHeight.perform(pressImeActionButton())

        labelValue.check(matches(withText("25.39")))
        labelClassification.check(matches(withText("Overweight")))
    }

}
