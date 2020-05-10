package com.htueko.android.journeyofseed.data.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class SwipeToDelete : ItemTouchHelper.SimpleCallback(
    0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
) {

    // to drag and drop, to move, we not gonna used here
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

}