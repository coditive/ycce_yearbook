package com.syrous.ycceyearbook.ui.notices

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syrous.ycceyearbook.databinding.PaperCardLayoutBinding
import com.syrous.ycceyearbook.model.Notification
import com.syrous.ycceyearbook.util.Truss

class NotificationAdapter(private val clickHandler: FragmentNotices.ClickHandler) : ListAdapter<Notification, NotificationAdapter.NotificationVH>(CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PaperCardLayoutBinding.inflate(inflater)
        return NotificationVH(binding)
    }

    override fun onBindViewHolder(holder: NotificationVH, position: Int) {
        holder.bind(getItem(position), clickHandler)
    }


    class NotificationVH(private val binding: PaperCardLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: Notification, clickHandler: FragmentNotices.ClickHandler) {
            binding.paperTitle.text = Truss()
                .append(notice.title)
                .append("\t")
                .append(notice.messageBody)
                .build()
            clickHandler.onClick(notice)
        }
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