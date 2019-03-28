package pl.krzysztofwojciechowski.bmi

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray

fun getPrefs(activity: Activity): SharedPreferences =
    activity.getSharedPreferences(HISTORY_PREFS_NAME, Context.MODE_PRIVATE)

fun readHistory(prefs: SharedPreferences): MutableList<HistoryEntry> {
    val historyString = prefs.getString("history", null)
    val history = ArrayList<HistoryEntry>()
    if (historyString == null) return history
    val jsonarray = JSONArray(historyString)
    history.ensureCapacity(jsonarray.length())

    for (i in 0..(jsonarray.length() - 1)) {
        val obj = jsonarray.getJSONObject(i)
        history.add(HistoryEntry(obj))
    }

    return history
}

fun saveHistory(history: List<HistoryEntry>, prefs: SharedPreferences) {
    val jsonHistory = history.map { entry -> entry.toJson() }
    with(prefs.edit()) {
        putString("history", JSONArray(jsonHistory).toString())
        apply() // asynchronous
    }
}
