package com.syrous.ycceyearbook.ui.semester

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.databinding.FragmentSemesterBinding
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.model.Department
import com.syrous.ycceyearbook.model.Semester
import com.syrous.ycceyearbook.model.Subject
import com.syrous.ycceyearbook.presenter.SemPresenter
import com.syrous.ycceyearbook.presenter.SemView
import com.syrous.ycceyearbook.ui.BaseFragment
import com.syrous.ycceyearbook.util.Constant
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import com.xwray.groupie.GroupieAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class FragmentSem : BaseFragment(), SemView {

    private lateinit var _binding: FragmentSemesterBinding
    private lateinit var department: Department
    private val semesterAdapter = GroupieAdapter()

    override val coroutineScope: CoroutineScope
        get() = viewLifecycleOwner.lifecycle.coroutineScope

    private val _backButtonClicks = MutableSharedFlow<Boolean>()
    override val backButtonClicks: SharedFlow<Boolean>
        get() = _backButtonClicks

    private val _departmentName = MutableSharedFlow<Department>()
    override val departmentName: SharedFlow<Department>
        get() = _departmentName

    private val _subjectClicks = MutableSharedFlow<Subject>()
    override val subjectClicks: SharedFlow<Subject>
        get() = _subjectClicks

    override var presenter: Presenter = SemPresenter(this)

    override fun showLoadingIndicator() {
        _binding.semesterLoadingView.visibility = View.VISIBLE
    }

    override fun hideLoadingIndicator() {
        _binding.semesterLoadingView.visibility = View.INVISIBLE
    }

    override fun addSemesterListToRecycler(semesterList: List<Semester>) {
        semesterAdapter.clear()
        val groupList = mutableListOf<Group>()
        for(sem in semesterList) {
          val section = ExpandableGroup(SemesterItem(sem.number)).apply {
               for(subject in sem.subjectList) {
                   add(SubjectItem(subject, viewLifecycleOwner.lifecycle.coroutineScope,
                       _subjectClicks))
               }
           }
            groupList.add(section)
        }
        semesterAdapter.update(groupList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enteringTransitionForFragment()
        _binding = FragmentSemesterBinding.inflate(layoutInflater, container, false)

        val sharedView = _binding.departmentNameText
        department = arguments?.getSerializable(Constant.Department.DEPARTMENT_NAME) as Department
        return _binding.root
    }

    private fun enteringTransitionForFragment() {
        enterTransition = MaterialFadeThrough().apply {
            addTarget(R.id.sem_recycler)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupThemeOfScreen()
        coroutineScope.launch {
            _departmentName.emit(department)
        }

        _binding.semesterLoadingView.setAnimation(Constant.Indicator.loading)


        _binding.semRecycler.apply {
            adapter = semesterAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun setupThemeOfScreen() {
        _binding.semToolbar.setBackgroundColor(ContextCompat.getColor(requireContext(),
            department.backgroundColor))
        _binding.departmentNameText.setBackgroundColor(ContextCompat.getColor(requireContext(),
            department.backgroundColor))
        _binding.departmentNameText.setCompoundDrawablesWithIntrinsicBounds(null,
            ContextCompat.getDrawable(requireContext(),
                department.largeDrawableId),null,null)

        _binding.semToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
            coroutineScope.launch {
                _backButtonClicks.emit(true)
            }
        }
    }

    override fun showAllViews() {
        _binding.apply {
            semRecycler.visibility = View.VISIBLE
        }
    }

    override fun hideAllViews() {
        _binding.apply {
            semRecycler.visibility = View.INVISIBLE
        }
    }
}
