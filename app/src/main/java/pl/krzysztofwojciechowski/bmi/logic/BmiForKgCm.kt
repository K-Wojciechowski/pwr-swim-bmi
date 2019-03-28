package pl.krzysztofwojciechowski.bmi.logic

import pl.krzysztofwojciechowski.bmi.BMI_METRIC_FACTOR
import pl.krzysztofwojciechowski.bmi.INVALID_HEIGHT_MSG
import pl.krzysztofwojciechowski.bmi.INVALID_MASS_MSG

class BmiForKgCm(private var mass: Int, private var height: Int) : Bmi {
    override fun countBmi(): Double {
        require(mass in 20..1500) { INVALID_MASS_MSG }
        require(height in 150..250) { INVALID_HEIGHT_MSG }
        return mass * BMI_METRIC_FACTOR / (height * height)
    }
}