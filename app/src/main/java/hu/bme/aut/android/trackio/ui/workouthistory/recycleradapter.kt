package hu.bme.aut.android.trackio.ui.workouthistory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.trackio.data.roomentities.Workout
import hu.bme.aut.android.trackio.databinding.WorkouthistoryitemBinding


class MyRecyclerViewAdapter(private val listener: WorkoutHistoryFragment) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.RecyclerViewHolder>() {
    //var last_workout_distance = 0.0F
    //var last_workout_type


    private var workoutHistoryList = mutableListOf<Workout>()

    inner class RecyclerViewHolder(val binding: WorkouthistoryitemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            WorkouthistoryitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = workoutHistoryList[position]
        holder.binding.root.setOnClickListener{
            listener.onItemClick(currentItem)
        }

    }

    override fun getItemCount(): Int {
        return workoutHistoryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(workouts: List<Workout>){
        this.workoutHistoryList=workouts.toMutableList()
        notifyDataSetChanged()
    }

}

