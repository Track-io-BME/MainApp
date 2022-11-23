package hu.bme.aut.android.trackio.ui.workoutmenu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.databinding.ActivechallengerowitemBinding
import java.text.SimpleDateFormat
import java.util.*

class ActiveChallengesAdapter :
    RecyclerView.Adapter<ActiveChallengesAdapter.ActiveChallengeViewHolder>() {


    private var activeChallengeList = mutableListOf<ActiveChallenge>()


    inner class ActiveChallengeViewHolder(val binding: ActivechallengerowitemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ActiveChallengeViewHolder(
        ActivechallengerowitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ActiveChallengeViewHolder, position: Int) {
        val currentItem = activeChallengeList[position]
        holder.binding.tvChallengeDuration.text= currentItem.duration.toString()
        holder.binding.tvDistance.text=getSportType(currentItem.sportType,currentItem.distance.toString())
        holder.binding.tvStartDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentItem.startDate)
    }

    override fun getItemCount(): Int {
        return activeChallengeList.size
    }

    private fun getSportType(type: ActiveChallenge.SportType, distance: String): String {
        return when (type) {
            ActiveChallenge.SportType.WALKING -> "Walk $distance km"
            ActiveChallenge.SportType.RUNNING -> "Run $distance km"
            ActiveChallenge.SportType.CYCLING -> "Cycle for $distance km"
        }
    }

    fun setData(activeChallenge: List<ActiveChallenge>){
        this.activeChallengeList=activeChallenge.toMutableList()
        notifyDataSetChanged()
    }
}