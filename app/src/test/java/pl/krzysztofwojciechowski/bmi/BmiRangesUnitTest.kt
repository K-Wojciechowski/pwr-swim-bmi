package pl.krzysztofwojciechowski.bmi

import org.junit.Test

import org.junit.Assert.*
import pl.krzysztofwojciechowski.bmi.logic.BmiForLbIn

class BmiRangesUnitTest {
    @Test
    fun testBmiToStringRes() {
        assertEquals(R.string.bmi_classification_empty, bmiToStringRes(0.0))
        assertEquals(R.string.bmi_classification_empty, bmiToStringRes(-5.0))
        assertEquals(R.string.bmi_classification_underweight, bmiToStringRes(0.000001))
        assertEquals(R.string.bmi_classification_underweight, bmiToStringRes(7.0))
        assertEquals(R.string.bmi_classification_underweight, bmiToStringRes(18.499))
        assertEquals(R.string.bmi_classification_normal, bmiToStringRes(18.5))
        assertEquals(R.string.bmi_classification_normal, bmiToStringRes(22.0))
        assertEquals(R.string.bmi_classification_normal, bmiToStringRes(24.999))
        assertEquals(R.string.bmi_classification_overweight, bmiToStringRes(25.0))
        assertEquals(R.string.bmi_classification_overweight, bmiToStringRes(27.0))
        assertEquals(R.string.bmi_classification_overweight, bmiToStringRes(29.999))
        assertEquals(R.string.bmi_classification_obese, bmiToStringRes(30.0))
        assertEquals(R.string.bmi_classification_obese, bmiToStringRes(31.0))
        assertEquals(R.string.bmi_classification_obese, bmiToStringRes(34.999))
        assertEquals(R.string.bmi_classification_extremely_obese, bmiToStringRes(35.0))
        assertEquals(R.string.bmi_classification_extremely_obese, bmiToStringRes(42.0))
        assertEquals(R.string.bmi_classification_extremely_obese, bmiToStringRes(1337.0))
    }

    @Test
    fun testBmiToLongTextRes() {
        assertEquals(R.string.bmi_longtext_empty, bmiToLongTextRes(0.0))
        assertEquals(R.string.bmi_longtext_empty, bmiToLongTextRes(-5.0))
        assertEquals(R.string.bmi_longtext_underweight, bmiToLongTextRes(0.000001))
        assertEquals(R.string.bmi_longtext_underweight, bmiToLongTextRes(7.0))
        assertEquals(R.string.bmi_longtext_underweight, bmiToLongTextRes(18.499))
        assertEquals(R.string.bmi_longtext_normal, bmiToLongTextRes(18.5))
        assertEquals(R.string.bmi_longtext_normal, bmiToLongTextRes(22.0))
        assertEquals(R.string.bmi_longtext_normal, bmiToLongTextRes(24.999))
        assertEquals(R.string.bmi_longtext_overweight, bmiToLongTextRes(25.0))
        assertEquals(R.string.bmi_longtext_overweight, bmiToLongTextRes(27.0))
        assertEquals(R.string.bmi_longtext_overweight, bmiToLongTextRes(29.999))
        assertEquals(R.string.bmi_longtext_obese, bmiToLongTextRes(30.0))
        assertEquals(R.string.bmi_longtext_obese, bmiToLongTextRes(31.0))
        assertEquals(R.string.bmi_longtext_obese, bmiToLongTextRes(34.999))
        assertEquals(R.string.bmi_longtext_extremely_obese, bmiToLongTextRes(35.0))
        assertEquals(R.string.bmi_longtext_extremely_obese, bmiToLongTextRes(42.0))
        assertEquals(R.string.bmi_longtext_extremely_obese, bmiToLongTextRes(1337.0))
    }

    @Test
    fun testBmiToColorRes() {
        assertEquals(R.color.bmi_color_empty, bmiToColorRes(0.0))
        assertEquals(R.color.bmi_color_empty, bmiToColorRes(-5.0))
        assertEquals(R.color.bmi_color_underweight, bmiToColorRes(0.000001))
        assertEquals(R.color.bmi_color_underweight, bmiToColorRes(7.0))
        assertEquals(R.color.bmi_color_underweight, bmiToColorRes(18.499))
        assertEquals(R.color.bmi_color_normal, bmiToColorRes(18.5))
        assertEquals(R.color.bmi_color_normal, bmiToColorRes(22.0))
        assertEquals(R.color.bmi_color_normal, bmiToColorRes(24.999))
        assertEquals(R.color.bmi_color_overweight, bmiToColorRes(25.0))
        assertEquals(R.color.bmi_color_overweight, bmiToColorRes(27.0))
        assertEquals(R.color.bmi_color_overweight, bmiToColorRes(29.999))
        assertEquals(R.color.bmi_color_obese, bmiToColorRes(30.0))
        assertEquals(R.color.bmi_color_obese, bmiToColorRes(31.0))
        assertEquals(R.color.bmi_color_obese, bmiToColorRes(34.999))
        assertEquals(R.color.bmi_color_extremely_obese, bmiToColorRes(35.0))
        assertEquals(R.color.bmi_color_extremely_obese, bmiToColorRes(42.0))
        assertEquals(R.color.bmi_color_extremely_obese, bmiToColorRes(1337.0))
    }
}
