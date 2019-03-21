package pl.krzysztofwojciechowski.bmi.logic

class BmiForKgCm(private var mass: Int, private var height: Int) : Bmi {
    override fun countBmi(): Double {
        require(mass in 20..1500) { "mass" }
        require(height in 150..250) { "height" }
//        require(height >= 150 && height <= 250) {"Invalid height"}
        return mass * 10000.0 / (height * height)
    }

//    override fun countBmi(): Double {
////        val bmi = mass * 10000.0 / (height * height)
////        return bmi
////    }
}