package pl.krzysztofwojciechowski.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    private var bmiValue: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        bmiValue = intent.getDoubleExtra("bmiValue", 0.0)
        bmi_info_number.text = formatBmiForDisplay(bmiValue)
        bmi_info_number.setBackgroundResource(bmiToColorRes(bmiValue))
        bmi_info_image.setBackgroundResource(bmiToColorRes(bmiValue))
        bmi_info_longtext.setText(bmiToLongTextRes(bmiValue))
    }
}
