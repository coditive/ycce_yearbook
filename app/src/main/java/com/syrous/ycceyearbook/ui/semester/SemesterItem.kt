package com.syrous.ycceyearbook.ui.semester

import android.view.View
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.databinding.SemesterHeaderItemLayoutBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.viewbinding.BindableItem

class SemesterItem(private val semester: Int
): BindableItem<SemesterHeaderItemLayoutBinding>(), ExpandableItem {
    private lateinit var expandableGroup: ExpandableGroup

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }

    override fun bind(viewBinding: SemesterHeaderItemLayoutBinding, position: Int) {
        viewBinding.semCardTextview.text = "Semester $semester"
        viewBinding.root.setOnClickListener {
            expandableGroup.onToggleExpanded()
        }
    }

    override fun getLayout(): Int = R.layout.semester_header_item_layout

    override fun initializeViewBinding(view: View): SemesterHeaderItemLayoutBinding =
        SemesterHeaderItemLayoutBinding.bind(view)
}