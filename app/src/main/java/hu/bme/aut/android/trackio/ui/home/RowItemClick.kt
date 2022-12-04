package hu.bme.aut.android.trackio.ui.home

import hu.bme.aut.android.trackio.data.roomentities.Workout

interface RowItemClick {
    fun onItemClick(currentItem: Workout)
}