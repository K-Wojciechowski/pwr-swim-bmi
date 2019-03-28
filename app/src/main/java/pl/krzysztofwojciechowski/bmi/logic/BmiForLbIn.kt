package pl.krzysztofwojciechowski.bmi.logic

import pl.krzysztofwojciechowski.bmi.BMI_IMPERIAL_FACTOR
import pl.krzysztofwojciechowski.bmi.INVALID_HEIGHT_MSG
import pl.krzysztofwojciechowski.bmi.INVALID_MASS_MSG

class BmiForLbIn(private var mass: Int, private var height: Int) : Bmi {
    override fun countBmi(): Double {
        require(mass in 40..3000) { INVALID_MASS_MSG }
        require(height in 60..100) { INVALID_HEIGHT_MSG }
        return mass * BMI_IMPERIAL_FACTOR / (height * height)
    }
}