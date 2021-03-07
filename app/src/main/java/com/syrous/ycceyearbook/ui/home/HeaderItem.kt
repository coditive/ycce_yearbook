package com.syrous.ycceyearbook.ui.home

import android.view.View
import androidx.annotation.StringRes
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.databinding.HeaderItemLayoutBinding
import com.xwray.groupie.viewbinding.BindableItem

open class HeaderItem(@StringRes private val titleResId: Int
): BindableItem<HeaderItemLayoutBinding>() {
    override fun bind(viewBinding: HeaderItemLayoutBinding, position: Int) {
        viewBinding.apply {
            title.setText(titleResId)
        }
    }

    override fun getLayout(): Int = R.layout.header_item_layout

    override fun initializeViewBinding(view: View): HeaderItemLayoutBinding =
        HeaderItemLayoutBinding.bind(view)

}