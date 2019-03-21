package pl.krzysztofwojciechowski.bmi.logic

class BmiForLbIn(private var mass: Int, private var height: Int) : Bmi {
    override fun countBmi(): Double {
        require(mass in 40..3000) { "mass" }
        require(height in 60..100) { "height" }
        return mass * 703.0 / (height * height)
    }
}