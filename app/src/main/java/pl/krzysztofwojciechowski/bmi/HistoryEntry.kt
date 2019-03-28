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
        j.getDouble(JSON_BMI_VALUE),
        j.getInt(JSON_MASS),
        j.getInt(JSON_HEIGHT),
        j.getBoolean(JSON_USES_METRIC),
        j.getString(JSON_DATE)
    )

    fun toJson(): JSONObject {
        return JSONObject(
            mutableMapOf(
                JSON_BMI_VALUE to bmiValue,
                JSON_MASS to mass,
                JSON_HEIGHT to height,
                JSON_USES_METRIC to usesMetric,
                JSON_DATE to date
            )
        )
    }
}
