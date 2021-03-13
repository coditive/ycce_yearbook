package com.syrous.ycceyearbook.ui.papers_and_resources

import android.view.View
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.databinding.ResourceCardLayoutBinding
import com.syrous.ycceyearbook.model.Resource
import com.xwray.groupie.viewbinding.BindableItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ResourceItem(
    private val resource: Resource,
    private val resourceClicks: MutableSharedFlow<Resource>,
    private val coroutineScope: CoroutineScope
): BindableItem<ResourceCardLayoutBinding>() {
    override fun bind(viewBinding: ResourceCardLayoutBinding, position: Int) {
        viewBinding.apply {
            resourceTitle.text = resource.title
            resourceDescription.text = resource.description
            root.setOnClickListener {
                coroutineScope.launch {
                    resourceClicks.emit(resource)
                }
            }
        }
    }

    override fun getLayout(): Int = R.layout.resource_card_layout

    override fun initializeViewBinding(view: View): ResourceCardLayoutBinding =
        ResourceCardLayoutBinding.bind(view)
}