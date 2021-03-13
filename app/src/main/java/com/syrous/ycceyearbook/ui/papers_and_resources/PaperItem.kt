package com.syrous.ycceyearbook.ui.papers_and_resources

import android.view.View
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.databinding.PaperCardLayoutBinding
import com.syrous.ycceyearbook.model.Paper
import com.xwray.groupie.viewbinding.BindableItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class PaperItem(
    private val paper: Paper,
    private val coroutineScope: CoroutineScope,
    private val paperClicks: MutableSharedFlow<Paper>
): BindableItem<PaperCardLayoutBinding>() {

    override fun bind(viewBinding: PaperCardLayoutBinding, position: Int) {
        viewBinding.paperTitle.text = paper.exam +"-"+ paper.year
        viewBinding.root.setOnClickListener {
            coroutineScope.launch {
                paperClicks.emit(paper)
            }
        }
    }

    override fun getLayout(): Int = R.layout.paper_card_layout

    override fun initializeViewBinding(view: View): PaperCardLayoutBinding =
        PaperCardLayoutBinding.bind(view)
}