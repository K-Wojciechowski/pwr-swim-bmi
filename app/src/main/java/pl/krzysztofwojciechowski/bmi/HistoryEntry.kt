package pl.krzysztofwojciechowski.bmi

import android.content.Context
import org.json.JSONObject

data class HistoryEntry(
    val bmiValue: Double, val mass: Int,
    val height: Int, val usesMetric: Boolean, val date: String
) {
    fun getMassAndHeightText(context: Context): String {
        val formatString =
            if (usesMetric) R.string.bmi_history_mass_and_height_metric else R.string.bmi_history_mass_and_height_imperial
        return context.getString(formatString, mass, height)
    }

    constructor(j: JSONObject) : this(
        j.getDouble("bmiValue"),
        j.getInt("mass"),
        j.getInt("height"),
        j.getBoolean("usesMetric"),
        j.getString("date")
    )

    fun toJson(): JSONObject {
        return JSONObject(
            mutableMapOf(
                "bmiValue" to bmiValue,
                "mass" to mass,
                "height" to height,
                "usesMetric" to usesMetric,
                "date" to date
            )
        )
    }
}
