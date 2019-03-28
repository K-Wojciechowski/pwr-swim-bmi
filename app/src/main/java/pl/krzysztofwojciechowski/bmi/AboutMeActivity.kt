package pl.krzysztofwojciechowski.bmi

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about_me.*

class AboutMeActivity : AppCompatActivity() {
    private var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)

        counter = savedInstanceState?.getInt(SIS_ABOUT_COUNTER) ?: 0
        bmi_about_colors.isChecked = savedInstanceState?.getBoolean(SIS_ABOUT_COLORS) ?: false
        setColors(bmi_about_colors.isChecked)
        setCounterText()

        bmi_about_colors.setOnCheckedChangeListener { _, isChecked ->
            setColors(isChecked)
        }
        bmi_about_click_me.setOnClickListener {
            counter += 1
            setCounterText()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putInt(SIS_ABOUT_COUNTER, counter)
        outState?.putBoolean(SIS_ABOUT_COLORS, bmi_about_colors.isChecked)
    }

    private fun setCounterText() {
        if (counter > 0)
            bmi_about_counter.text = getString(R.string.bmi_about_clicked_n_times, counter)
        else
            bmi_about_counter.setText(R.string.bmi_about_click_zero)
    }

    private fun setColors(isChecked: Boolean) {
        if (isChecked) {
            bmi_about_name.setBackgroundResource(R.color.kw_vivid_cerulean)
            bmi_about_name.setTextColor(Color.WHITE)
        } else {
            bmi_about_name.setBackgroundColor(Color.TRANSPARENT)
            bmi_about_name.setTextColor(Color.BLACK)
        }
    }
}
