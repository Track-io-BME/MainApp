package hu.bme.aut.android.trackio.ui.workouthistory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.trackio.R
import org.w3c.dom.Text

class MyRecyclerViewAdapter(private val items: List<RecyclerViewData>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewDataHolder>() {

    inner class MyRecyclerViewDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerViewDataHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_data_view, parent, false)
        return MyRecyclerViewDataHolder(view)
    }

    override fun onBindViewHolder(holder: MyRecyclerViewDataHolder, position: Int) {
        val currentItem: RecyclerViewData = items[position]

        val WOType: TextView = holder.itemView.findViewById(R.id.workout_history_type_of_workout)
        WOType.text = currentItem.WorkOutType

        val WODuration: TextView = holder.itemView.findViewById(R.id.workout_history_timer)
        WODuration.text = currentItem.WorkOutDuration

        val WOTime: TextView = holder.itemView.findViewById(R.id.workout_history_start_time)
        WOTime.text = currentItem.WorkOutDateHour

        val WODate: TextView = holder.itemView.findViewById(R.id.workout_history_date)
        WODate.text = currentItem.WorkOutDate
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

