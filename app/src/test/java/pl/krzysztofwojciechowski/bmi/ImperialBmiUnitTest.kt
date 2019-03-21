package pl.krzysztofwojciechowski.bmi

import org.junit.Test

import org.junit.Assert.*
import pl.krzysztofwojciechowski.bmi.logic.BmiForLbIn

class ImperialBmiUnitTest {
    @Test
    fun for_valid_data_should_count_bmi() {
        val bmi = BmiForLbIn(155, 60)
        assertEquals(30.268, bmi.countBmi(), 0.001)
    }

    @Test(expected = IllegalArgumentException::class)
    fun for_invalid_data_should_count_bmi() {
        val bmi = BmiForLbIn(40, 17)
        bmi.countBmi()
    }
}
