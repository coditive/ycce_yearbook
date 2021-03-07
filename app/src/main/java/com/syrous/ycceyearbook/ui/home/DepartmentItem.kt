package com.syrous.ycceyearbook.ui.home

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.databinding.DepartmentCardLayoutBinding
import com.syrous.ycceyearbook.model.Department
import com.xwray.groupie.viewbinding.BindableItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class DepartmentItem(
    private val department: Department,
    private val context: Context,
    private val departmentClicks: MutableSharedFlow<Department>,
    private val coroutineScope: CoroutineScope
    ): BindableItem<DepartmentCardLayoutBinding>() {
    init {
        extras[INSET_TYPE_KEY] = INSET
    }

    override fun bind(viewBinding: DepartmentCardLayoutBinding, position: Int) {
        viewBinding.departmentName.text = department.name
        viewBinding.departmentName.setCompoundDrawablesRelativeWithIntrinsicBounds(
            ContextCompat.getDrawable(context, department.smallDrawableId),
            null, null, null
        )
        viewBinding.departmentCard.setCardBackgroundColor(ContextCompat.getColor(context,
            department.backgroundColor))

        viewBinding.root.setOnClickListener {
           coroutineScope.launch {
               departmentClicks.emit(department)
           }
        }
    }

    override fun getLayout(): Int = R.layout.department_card_layout

    override fun initializeViewBinding(view: View): DepartmentCardLayoutBinding =
        DepartmentCardLayoutBinding.bind(view)

    override fun getSpanSize(spanCount: Int, position: Int): Int = spanCount / 2

    override fun getDragDirs(): Int =
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP or ItemTouchHelper.DOWN

}