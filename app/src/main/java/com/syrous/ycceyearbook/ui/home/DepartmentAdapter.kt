package com.syrous.ycceyearbook.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syrous.ycceyearbook.databinding.DepartmentCardLayoutBinding

class DepartmentAdapter(
    private val departmentList: List<Department>
): RecyclerView.Adapter<DepartmentAdapter.DptViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DptViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DepartmentCardLayoutBinding.inflate(inflater, parent, false)
        return DptViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return departmentList.size
    }

    override fun onBindViewHolder(holder: DptViewHolder, position: Int) {
        holder.bind(departmentList[position])
    }

    class DptViewHolder ( private val binding: DepartmentCardLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(department: Department) {

            binding.departmentCard.apply {
                setCardBackgroundColor(resources.getColor(department.backgroundColor))
            }

            binding.departmentName.apply {
                text = department.name
                setTextColor(resources.getColor(department.textColor))
                setCompoundDrawablesWithIntrinsicBounds(department.drawableId,0,0,0)
            }

        }
    }

}