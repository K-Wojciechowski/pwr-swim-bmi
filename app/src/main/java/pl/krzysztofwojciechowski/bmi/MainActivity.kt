package pl.krzysztofwojciechowski.bmi

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import pl.krzysztofwojciechowski.bmi.logic.Bmi
import pl.krzysztofwojciechowski.bmi.logic.BmiForKgCm
import pl.krzysztofwojciechowski.bmi.logic.BmiForLbIn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var bmiValue = 0.0
    private var usesMetric = true
    private var everCalculated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bmi_main_edit_mass.setText(savedInstanceState?.getString(SIS_MASS) ?: "")
        bmi_main_edit_height.setText(savedInstanceState?.getString(SIS_HEIGHT) ?: "")
        bmi_main_edit_height.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                calculateBmiFromUI()
                false
            } else false
        }
        usesMetric = savedInstanceState?.getBoolean(SIS_USES_METRIC) ?: true
        everCalculated = savedInstanceState?.getBoolean(SIS_EVER_CALCULATED) ?: false
        setUnitLabels()
        if (everCalculated) {
            calculateBmiFromUI()
        }
        bmi_main_btn_calculate.setOnClickListener { calculateBmiFromUI() }
        bmi_main_fab.setOnClickListener {
            val infoIntent = Intent(this, InfoActivity::class.java)
            infoIntent.putExtra(INTENTEXTRA_BMI_VALUE, bmiValue)
            startActivity(infoIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.bmi_menu_about_me -> {
                val aboutIntent = Intent(this, AboutMeActivity::class.java)
                startActivity(aboutIntent)
                true
            }
            R.id.bmi_menu_change_units -> {
                bmi_main_edit_mass.setText("")
                bmi_main_edit_height.setText("")
                usesMetric = !usesMetric
                setUnitLabels()
                calculateBmiFromUI(true)
                showUnitChangeMessage()
                true
            }
            R.id.bmi_menu_history -> {
                val historyIntent = Intent(this, HistoryActivity::class.java)
                startActivity(historyIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUnitLabels() {
        if (usesMetric) {
            bmi_main_label_mass.setText(R.string.bmi_mass_kg)
            bmi_main_label_height.setText(R.string.bmi_height_cm)
        } else {
            bmi_main_label_mass.setText(R.string.bmi_mass_lb)
            bmi_main_label_height.setText(R.string.bmi_height_in)
        }
    }

    private fun showUnitChangeMessage() {
        val toastMessage = if (usesMetric) {
            R.string.bmi_units_changed_to_metric
        } else {
            R.string.bmi_units_changed_to_imperial
        }
        Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_SHORT).show()
    }

    private fun calculateBmiFromUI(hideEmptyErrors: Boolean = false) {
        val massText = bmi_main_edit_mass.text.toString()
        val heightText = bmi_main_edit_height.text.toString()
        if ((massText.isBlank() && heightText.isBlank()) || (hideEmptyErrors && (massText.isBlank() || heightText.isBlank()))) {
            bmiValue = 0.0
            setValueTextAndColor()
            setClassification()
            return
        }
        everCalculated = true

        val mass: Int
        val height: Int
        try {
            mass = massText.toInt()
            bmi_main_edit_mass.error = null
        } catch (e: NumberFormatException) {
            bmi_main_edit_mass.error = getString(R.string.bmi_error_invalid_mass)
            return
        }
        try {
            height = heightText.toInt()
            bmi_main_edit_height.error = null
        } catch (e: NumberFormatException) {
            bmi_main_edit_height.error = getString(R.string.bmi_error_invalid_height)
            return
        }

        val bmiClass: Bmi = if (usesMetric) {
            BmiForKgCm(mass, height)
        } else {
            BmiForLbIn(mass, height)
        }

        try {
            bmiValue = bmiClass.countBmi()
            setValueTextAndColor()
            setClassification()
            saveHistory(bmiValue, mass, height, usesMetric)
        } catch (i: IllegalArgumentException) {
            if (i.message == INVALID_MASS_MSG) bmi_main_edit_mass.error = getString(R.string.bmi_error_invalid_mass)
            if (i.message == INVALID_HEIGHT_MSG) bmi_main_edit_height.error = getString(R.string.bmi_error_invalid_height)
            bmiValue = 0.0
            setValueColor()
            bmi_main_label_value.setText(R.string.bmi_invalid_value)
            bmi_main_label_classification.setText(R.string.bmi_classification_unknown)
        }
    }

    private fun saveHistory(bmiValue: Double, mass: Int, height: Int, usesMetric: Boolean) {
        val time = Calendar.getInstance().time
        val format = SimpleDateFormat(DATE_FORMAT, Locale.UK)
        val date = format.format(time)
        val entry = HistoryEntry(bmiValue, mass, height, usesMetric, date)

        // run in the background. The method is synchronized to avoid losing data.
        Thread { addToHistory(entry) }.start()
    }

    @Synchronized
    private fun addToHistory(entry: HistoryEntry) {
        val prefs = getPrefs(this)
        var history = readHistory(prefs)
        if (history.size == 10) history = history.subList(1, history.size)
        history.add(entry)
        saveHistory(history, prefs)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SIS_MASS, bmi_main_edit_mass.text.toString())
        outState.putString(SIS_HEIGHT, bmi_main_edit_height.text.toString())
        outState.putBoolean(SIS_USES_METRIC, usesMetric)
        outState.putBoolean(SIS_EVER_CALCULATED, everCalculated)
    }

    private fun setValueTextAndColor() {
        bmi_main_label_value.text = formatBmiForDisplay(bmiValue)
        setValueColor()
    }

    private fun setValueColor() {
        bmi_main_label_value.setTextColor(ContextCompat.getColor(applicationContext, bmiToColorRes(bmiValue)))
    }

    private fun setClassification() {
        bmi_main_label_classification.setText(bmiToStringRes(bmiValue))
    }
}
