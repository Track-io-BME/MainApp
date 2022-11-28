package hu.bme.aut.android.trackio.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.data.roomentities.Workout
import hu.bme.aut.android.trackio.databinding.WorkouthistoryitemBinding
import java.text.SimpleDateFormat
import java.util.*

class WorkoutAdapter(private val listener: RowItemClick) : RecyclerView.Adapter<WorkoutAdapter.WorkoutHistoryViewHolder>() {

    private var workoutHistoryList = mutableListOf<Workout>()

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
        val currentItem = workoutHistoryList[position]
        holder.binding.ivSportType.setImageResource(getImageResource(currentItem.sportType))
        holder.binding.tvSportType.text =
            getSportTypeAndCalories(currentItem.sportType, String.format("%.2f", currentItem.distance), currentItem.calories.toInt())
        holder.binding.tvPace.text = String.format("%.2f m/s",currentItem.averageSpeed)
        holder.binding.tvDate.text =
            SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()).format(currentItem.date)

        val seconds = currentItem.totalduration % 60
        var minutes = currentItem.totalduration / 60
        val hours = minutes / 60
        minutes %= 60
        holder.binding.tvDuration.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
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

    @DrawableRes
    private fun getImageResource(sportType: ActiveChallenge.SportType): Int {
        return when (sportType) {
            ActiveChallenge.SportType.WALKING -> R.drawable.white_walk
            ActiveChallenge.SportType.RUNNING -> R.drawable.white_run
            ActiveChallenge.SportType.CYCLING -> R.drawable.white_bike
        }
    }

    private fun getSportTypeAndCalories(type: ActiveChallenge.SportType, distance: String, calories : Int): String {
        return when (type) {
            ActiveChallenge.SportType.WALKING -> "Walk $distance km \nBurned $calories calories"
            ActiveChallenge.SportType.RUNNING -> "Run $distance km \nBurned $calories calories"
            ActiveChallenge.SportType.CYCLING -> "Cycle for $distance km \nBurned $calories calories"
        }
    }

}