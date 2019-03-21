package pl.krzysztofwojciechowski.bmi.logic

class BmiForKgCm(private var mass: Int, private var height: Int) : Bmi {
    override fun countBmi(): Double {
        require(mass in 20..1500) { "mass" }
        require(height in 150..250) { "height" }
        return mass * 10000.0 / (height * height)
    }
}