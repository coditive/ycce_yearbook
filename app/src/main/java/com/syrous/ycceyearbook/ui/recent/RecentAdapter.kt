package com.syrous.ycceyearbook.ui.recent

import android.text.style.AbsoluteSizeSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syrous.ycceyearbook.databinding.RecentCardLayoutBinding
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.ui.recent.FragmentRecent.ClickHandler
import com.syrous.ycceyearbook.util.Truss

class RecentAdapter (private val clickHandler: ClickHandler):
    ListAdapter<Paper, RecentAdapter.RecentViewHolder>(CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecentCardLayoutBinding.inflate(inflater)
        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(getItem(position), clickHandler)
    }

    class RecentViewHolder(private val binding: RecentCardLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(paper: Paper, clickHandler: ClickHandler) {
            binding.apply {
                recentTitle.text = Truss()
                    .pushSpan(AbsoluteSizeSpan(18, true))
                    .append(paper.sem.toString() + "-" + paper.exam.toUpperCase() + "-" + paper.courseCode.toUpperCase())
                    .build()
                root.setOnClickListener {
                    clickHandler.onClick(paper)
                }
            }
        }
    }

    companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<Paper>(){
            override fun areItemsTheSame(oldItem: Paper, newItem: Paper): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Paper, newItem: Paper): Boolean {
                return oldItem.courseCode == newItem.courseCode
            }
        }
    }
}

