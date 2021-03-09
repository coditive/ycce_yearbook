package com.syrous.ycceyearbook.ui.papers_and_resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.syrous.ycceyearbook.databinding.FragmentPaperAndResourceDetailBinding
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.model.Paper
import com.syrous.ycceyearbook.model.Subject
import com.syrous.ycceyearbook.presenter.MsePresenter
import com.syrous.ycceyearbook.presenter.MseView
import com.syrous.ycceyearbook.ui.BaseFragment
import com.syrous.ycceyearbook.util.SUBJECT_OBJECT
import com.xwray.groupie.Group
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@FlowPreview
class FragmentMse : BaseFragment(), MseView {
    private lateinit var subject: Subject
    private lateinit var binding: FragmentPaperAndResourceDetailBinding
    override var presenter: Presenter = MsePresenter(this)
    private val mseAdapter = GroupieAdapter()
    private val _selectedSubject = MutableSharedFlow<Subject>()
    override val selectedSubject: SharedFlow<Subject>
        get() = _selectedSubject

    private val _msePaperClicks = MutableSharedFlow<Paper>()
    override val msePaperClicks: SharedFlow<Paper>
        get() = _msePaperClicks

    override val coroutineScope: CoroutineScope
        get() = viewLifecycleOwner.lifecycle.coroutineScope

    override fun hideAllViews() {
        binding.paperAndResourceRecycler.visibility = View.INVISIBLE
    }

    override fun showAllViews() {
        binding.paperAndResourceRecycler.visibility = View.INVISIBLE
    }

    override fun showLoadingIndicator() {
        binding.paperAndResourceLoadingView.visibility = View.VISIBLE
    }

    override fun hideLoadingIndicator() {
        binding.paperAndResourceLoadingView.visibility = View.GONE
    }

    override fun addPaperToRecycler(paperList: List<Paper>) {
        mseAdapter.clear()
        val paperData = mutableListOf<Group>()
        val paperSection = Section()
        for (paper in paperList) {
            paperSection.add(PaperItem(paper, coroutineScope, _msePaperClicks))
        }
        paperData.add(paperSection)
        mseAdapter.update(paperData)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaperAndResourceDetailBinding.inflate(inflater, container, false)
        subject = arguments?.getSerializable(SUBJECT_OBJECT) as Subject
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coroutineScope.launch {
            _selectedSubject.emit(subject)
        }

        binding.paperAndResourceRecycler.apply {
            adapter = mseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}