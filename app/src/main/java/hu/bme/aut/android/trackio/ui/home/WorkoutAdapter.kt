package hu.bme.aut.android.trackio.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.data.roomentities.Workout
import hu.bme.aut.android.trackio.databinding.WorkouthistoryitemBinding
import java.text.SimpleDateFormat
import java.util.*

class WorkoutAdapter(private val listener: HomeFragment) : RecyclerView.Adapter<WorkoutAdapter.WorkoutHistoryViewHolder>() {

    private var workouthistoryList = mutableListOf<Workout>()

    inner class WorkoutHistoryViewHolder(val binding: WorkouthistoryitemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutHistoryViewHolder {
        return WorkoutHistoryViewHolder(
            WorkouthistoryitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WorkoutHistoryViewHolder, position: Int) {
        val currentItem = workouthistoryList[position]
        holder.binding.ivSportType.setImageResource(getImageResource(currentItem.sportType))
        holder.binding.tvSportType.text =
            getSportType(currentItem.sportType, currentItem.distance.toString())
        holder.binding.tvPace.text = currentItem.averageSpeed.toString()
        holder.binding.tvDate.text =
            SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()).format(currentItem.date)
        holder.binding.tvDuration.text=SimpleDateFormat("HH:mm", Locale.getDefault()).format(currentItem.totalduration)
        holder.binding.root.setOnClickListener{
            listener.onItemClick(currentItem)
        }
    }



    override fun getItemCount(): Int {
        return workouthistoryList.size
    }

    fun setData(workouts: List<Workout>){
        this.workouthistoryList=workouts.toMutableList()
        notifyDataSetChanged()
    }

    @DrawableRes()
    private fun getImageResource(sportType: ActiveChallenge.SportType): Int {
        return when (sportType) {
            ActiveChallenge.SportType.WALKING -> R.drawable.white_walk
            ActiveChallenge.SportType.RUNNING -> R.drawable.white_run
            ActiveChallenge.SportType.CYCLING -> R.drawable.white_bike
        }
    }

    private fun getSportType(type: ActiveChallenge.SportType, distance: String): String {
        return when (type) {
            ActiveChallenge.SportType.WALKING -> "Walk $distance km"
            ActiveChallenge.SportType.RUNNING -> "Run $distance km"
            ActiveChallenge.SportType.CYCLING -> "Cycle for $distance km"
        }
    }

}