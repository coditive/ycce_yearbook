package com.syrous.ycceyearbook.ui.home

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.databinding.OtherFeaturesCardLayoutBinding
import com.xwray.groupie.viewbinding.BindableItem

class OtherFeatureItem(private val otherFeature: OtherFeature,
                       private val context: Context
): BindableItem<OtherFeaturesCardLayoutBinding>() {
    override fun bind(viewBinding: OtherFeaturesCardLayoutBinding, position: Int) {
        viewBinding.otherFeatureItem.setCompoundDrawablesRelativeWithIntrinsicBounds(
            null, ContextCompat.getDrawable(context, otherFeature.drawableId),
            null, null
        )
        viewBinding.otherFeatureItem.text = otherFeature.name
    }

    override fun getLayout(): Int = R.layout.other_features_card_layout

    override fun initializeViewBinding(view: View): OtherFeaturesCardLayoutBinding =
        OtherFeaturesCardLayoutBinding.bind(view)
}