package pl.krzysztofwojciechowski.bmi

import org.junit.Test

import org.junit.Assert.*
import pl.krzysztofwojciechowski.bmi.logic.BmiForKgCm

class MetricUnitTest {
    @Test
    fun for_valid_data_should_count_bmi() {
        val bmi = BmiForKgCm(65, 170)
        assertEquals(22.491, bmi.countBmi(), 0.001)
    }

    @Test(expected = IllegalArgumentException::class)
    fun for_invalid_data_should_count_bmi() {
        val bmi = BmiForKgCm(5, 17)
        bmi.countBmi()
    }
}
