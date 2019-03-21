package pl.krzysztofwojciechowski.bmi

import org.junit.Test

import org.junit.Assert.*
import pl.krzysztofwojciechowski.bmi.logic.BmiForLbIn

class StringFormattingUnitTest {
    @Test
    fun test_bmi_string_formatting() {
        assertEquals("0.00", formatBmiForDisplay(0.0))
        assertEquals("1.00", formatBmiForDisplay(1.0))
        assertEquals("2.30", formatBmiForDisplay(2.3))
        assertEquals("1337.42", formatBmiForDisplay(1337.42))
        assertEquals("1.23", formatBmiForDisplay(1.23456789))
        assertEquals("56.79", formatBmiForDisplay(56.789))
    }
}
