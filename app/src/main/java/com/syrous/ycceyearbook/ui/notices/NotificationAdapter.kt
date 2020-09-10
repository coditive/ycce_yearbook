package com.syrous.ycceyearbook.ui.notices

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syrous.ycceyearbook.model.Notification

class NotificationAdapter : ListAdapter<Notification, NotificationAdapter.NotificationVH>(CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationVH {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NotificationVH, position: Int) {
        TODO("Not yet implemented")
    }


    class NotificationVH(private val itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<Notification> () {
            override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem.timestamp == newItem.timestamp
            }
        }
    }
}