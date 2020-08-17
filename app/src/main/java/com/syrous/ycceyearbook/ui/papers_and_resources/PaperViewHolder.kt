package com.syrous.ycceyearbook.ui.papers_and_resources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syrous.ycceyearbook.databinding.PaperCardLayoutBinding
import com.syrous.ycceyearbook.model.Paper

class PaperAdapter: ListAdapter<Paper, PaperAdapter.PaperViewHolder>(CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaperViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PaperCardLayoutBinding.inflate(inflater)
        return PaperViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaperViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PaperViewHolder(private val binding: PaperCardLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(paper: Paper) {
            binding.paperTitle.text = paper.id
        }
    }

    companion object {
        val CALLBACK = object : DiffUtil.ItemCallback<Paper>() {
            override fun areItemsTheSame(oldItem: Paper, newItem: Paper): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Paper, newItem: Paper): Boolean {
                return oldItem.courseCode == newItem.courseCode
            }

        }
    }
}