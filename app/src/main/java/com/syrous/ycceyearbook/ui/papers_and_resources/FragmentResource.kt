package com.syrous.ycceyearbook.ui.papers_and_resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.syrous.ycceyearbook.databinding.FragmentPaperAndResourceDetailBinding
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.model.Resource
import com.syrous.ycceyearbook.model.Subject
import com.syrous.ycceyearbook.presenter.ResourcePresenter
import com.syrous.ycceyearbook.presenter.ResourceView
import com.syrous.ycceyearbook.ui.BaseFragment
import com.syrous.ycceyearbook.util.Constant
import com.syrous.ycceyearbook.util.SUBJECT_OBJECT
import com.xwray.groupie.Group
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class FragmentResource : BaseFragment(), ResourceView {

    private lateinit var binding: FragmentPaperAndResourceDetailBinding
    override var presenter: Presenter = ResourcePresenter(this)
    private val _selectedSubject = MutableSharedFlow<Subject>()
    override val selectedSubject: SharedFlow<Subject>
        get() = _selectedSubject

    override val coroutineScope: CoroutineScope
        get() = viewLifecycleOwner.lifecycle.coroutineScope

    private val _resourceClicks = MutableSharedFlow<Resource>()
    override val resourceClicks: SharedFlow<Resource>
        get() = _resourceClicks

    private val resourceAdapter = GroupieAdapter()
    private lateinit var subject: Subject

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaperAndResourceDetailBinding.inflate(inflater, container, false)
        subject = arguments?.getSerializable(SUBJECT_OBJECT) as Subject
        Timber.d("Resource Fragment Initialized and subject is $subject")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.paperAndResourceLoadingView.setAnimation(Constant.Indicator.loading)
        coroutineScope.launch {
            _selectedSubject.emit(subject)
        }

        binding.paperAndResourceRecycler.apply {
            adapter = resourceAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    override fun hideAllViews() {
        binding.paperAndResourceRecycler.visibility = View.INVISIBLE
    }

    override fun showAllViews() {
        binding.paperAndResourceRecycler.visibility = View.VISIBLE
    }

    override fun showLoadingIndicator() {
        binding.paperAndResourceLoadingView.visibility = View.VISIBLE
    }

    override fun hideLoadingIndicator() {
        binding.paperAndResourceLoadingView.visibility = View.GONE
    }

    override fun addResourceToRecycler(resourceList: List<Resource>) {
        resourceAdapter.clear()
        val resourceData = mutableListOf<Group>()
        val resourceSection = Section()
        for(resource in resourceList) {
            if(resource.contentType == "video") {
                resourceSection.add(ResourceItem(resource, _resourceClicks, coroutineScope))
            } else if(resource.contentType == "pdf") {
                resourceSection.add(ResourcePdfItem(resource, _resourceClicks, coroutineScope))
            }
        }
        resourceData.add(resourceSection)
        resourceAdapter.update(resourceData)
    }
}