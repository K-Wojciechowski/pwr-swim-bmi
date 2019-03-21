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
        editMass.setText(savedInstanceState?.getString("mass") ?: "")
        editHeight.setText(savedInstanceState?.getString("height") ?: "")
        editHeight.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                calculateBmiFromUI()
                false
            } else false
        }
        usesMetric = savedInstanceState?.getBoolean("usesMetric") ?: true
        everCalculated = savedInstanceState?.getBoolean("everCalculated") ?: false
        setUnitLabels()
        if (everCalculated) {
            calculateBmiFromUI()
        }
        btnCalculate.setOnClickListener { calculateBmiFromUI() }
        fab.setOnClickListener {
            val infoIntent = Intent(this, InfoActivity::class.java)
            infoIntent.putExtra("bmiValue", bmiValue)
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
                editMass.setText("")
                editHeight.setText("")
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
            labelMass.setText(R.string.bmi_mass_kg)
            labelHeight.setText(R.string.bmi_height_cm)
        } else {
            labelMass.setText(R.string.bmi_mass_lb)
            labelHeight.setText(R.string.bmi_height_in)
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
        val massText = editMass.text.toString()
        val heightText = editHeight.text.toString()
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
            editMass.error = null
        } catch (e: NumberFormatException) {
            editMass.error = getString(R.string.bmi_error_invalid_mass)
            return
        }
        try {
            height = heightText.toInt()
            editHeight.error = null
        } catch (e: NumberFormatException) {
            editHeight.error = getString(R.string.bmi_error_invalid_height)
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
            if (i.message == "mass") editMass.error = getString(R.string.bmi_error_invalid_mass)
            if (i.message == "height") editHeight.error = getString(R.string.bmi_error_invalid_height)
            bmiValue = 0.0
            setValueColor()
            labelValue.setText(R.string.bmi_invalid_value)
            labelClassification.setText(R.string.bmi_classification_unknown)
        }
    }

    private fun saveHistory(bmiValue: Double, mass: Int, height: Int, usesMetric: Boolean) {
        val time = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
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
        outState.putString("mass", editMass.text.toString())
        outState.putString("height", editHeight.text.toString())
        outState.putBoolean("usesMetric", usesMetric)
    }

    private fun setValueTextAndColor() {
        labelValue.text = formatBmiForDisplay(bmiValue)
        setValueColor()
    }

    private fun setValueColor() {
        labelValue.setTextColor(ContextCompat.getColor(applicationContext, bmiToColorRes(bmiValue)))
    }

    private fun setClassification() {
        labelClassification.setText(bmiToStringRes(bmiValue))
    }
}
