package pl.krzysztofwojciechowski.bmi

import java.text.DecimalFormat

fun formatBmiForDisplay(bmi: Double): String {
    return DecimalFormat("0.00").format(bmi)
}

fun bmiToStringRes(bmi: Double): Int {
    return when {
        bmi <= 0   -> R.string.bmi_classification_empty
        bmi < 18.5 -> R.string.bmi_classification_underweight
        bmi < 25   -> R.string.bmi_classification_normal
        bmi < 30   -> R.string.bmi_classification_overweight
        bmi < 35   -> R.string.bmi_classification_obese
        else       -> R.string.bmi_classification_extremely_obese
    }
}

fun bmiToLongTextRes(bmi: Double): Int {
    return when {
        bmi <= 0   -> R.string.bmi_longtext_empty
        bmi < 18.5 -> R.string.bmi_longtext_underweight
        bmi < 25   -> R.string.bmi_longtext_normal
        bmi < 30   -> R.string.bmi_longtext_overweight
        bmi < 35   -> R.string.bmi_longtext_obese
        else       -> R.string.bmi_longtext_extremely_obese
    }
}

fun bmiToColorRes(bmi: Double): Int {
    return when {
        bmi <= 0   -> R.color.bmi_color_empty
        bmi < 18.5 -> R.color.bmi_color_underweight
        bmi < 25   -> R.color.bmi_color_normal
        bmi < 30   -> R.color.bmi_color_overweight
        bmi < 35   -> R.color.bmi_color_obese
        else       -> R.color.bmi_color_extremely_obese
    }
}
