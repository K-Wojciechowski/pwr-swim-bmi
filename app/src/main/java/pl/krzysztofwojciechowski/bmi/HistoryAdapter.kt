package pl.krzysztofwojciechowski.bmi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private var historyItems: List<HistoryEntry> = listOf()) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class HistoryViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {
        val valueView: TextView = itemView.findViewById(R.id.bmi_history_value)
        val massAndHeightView: TextView = itemView.findViewById(R.id.bmi_history_mass_and_height)
        val dateView: TextView = itemView.findViewById(R.id.bmi_history_date)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_list_item, parent, false),
            parent.context
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        // newest at top
        val item = historyItems[historyItems.size - 1 - position]
        holder.valueView.text = formatBmiForDisplay(item.bmiValue)
        holder.valueView.setTextColor(ContextCompat.getColor(holder.context, bmiToColorRes(item.bmiValue)))
        holder.dateView.text = item.date
        holder.massAndHeightView.text = item.getMassAndHeightText(holder.context)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = historyItems.size

    fun setItems(historyItems: List<HistoryEntry>) {
        this.historyItems = historyItems
        notifyDataSetChanged()
    }
}