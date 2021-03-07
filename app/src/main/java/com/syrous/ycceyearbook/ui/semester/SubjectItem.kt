package com.syrous.ycceyearbook.ui.semester

import android.view.View
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.databinding.SubjectCardLayoutBinding
import com.syrous.ycceyearbook.model.Subject
import com.xwray.groupie.viewbinding.BindableItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SubjectItem(
    private val subject: Subject,
    private val coroutineScope: CoroutineScope,
    private val subjectClicks: MutableSharedFlow<Subject>
    ): BindableItem<SubjectCardLayoutBinding>() {
    override fun bind(viewBinding: SubjectCardLayoutBinding, position: Int) {
        viewBinding.semCardTextview.text = subject.course
        viewBinding.root.setOnClickListener {
            coroutineScope.launch {
                subjectClicks.emit(subject)
            }
        }
    }

    override fun getLayout(): Int = R.layout.subject_card_layout

    override fun initializeViewBinding(view: View): SubjectCardLayoutBinding =
        SubjectCardLayoutBinding.bind(view)
}