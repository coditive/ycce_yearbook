package com.syrous.ycceyearbook.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.syrous.ycceyearbook.databinding.OtherFeaturesCardLayoutBinding

class OtherFeaturesAdapter(
    private val otherFeatures: List<OtherFeature>,
    private val clickHandler: FragmentHome.OtherFeatureClickHandler
) : RecyclerView.Adapter<OtherFeaturesAdapter.OtherFeaturesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherFeaturesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OtherFeaturesCardLayoutBinding.inflate(inflater, parent, false)
        return OtherFeaturesViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return otherFeatures.size
    }

    override fun onBindViewHolder(holder: OtherFeaturesViewHolder, position: Int) {
        holder.onBind(otherFeatures[position])
        holder.itemView.setOnClickListener {
            clickHandler.clickListener(position)
        }
    }

    class OtherFeaturesViewHolder(private val binding: OtherFeaturesCardLayoutBinding):
                RecyclerView.ViewHolder(binding.root) {

        fun onBind(feature: OtherFeature) {
            binding.otherFeatureItem.text = feature.name
            binding.otherFeatureItem.setCompoundDrawablesWithIntrinsicBounds(0, feature.drawableId,0,0)
        }
    }

}
