package pl.krzysztofwojciechowski.bmi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: HistoryAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


        viewManager = LinearLayoutManager(this)
        viewAdapter = HistoryAdapter()

        recyclerView = findViewById<RecyclerView>(R.id.bmi_history_recyclerview).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }

        Thread {
            val history = readHistory(getPrefs(this))
            runOnUiThread { viewAdapter.setItems(history) }
        }.start()
    }
}
