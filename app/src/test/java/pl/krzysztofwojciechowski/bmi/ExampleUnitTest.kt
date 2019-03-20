package pl.krzysztofwojciechowski.bmi

import org.junit.Test

import org.junit.Assert.*
import pl.krzysztofwojciechowski.bmi.logic.BmiForKgCm

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun for_valid_data_should_count_bmi() {
        val bmi = BmiForKgCm(65, 170)
        assertEquals(22.491, bmi.countBmi(), 0.001)
    }

    fun for_negative_data_should_count_bmi() {
        val bmi = BmiForKgCm(5, 17)
        bmi.countBmi()
        // todo throws
    }
}
