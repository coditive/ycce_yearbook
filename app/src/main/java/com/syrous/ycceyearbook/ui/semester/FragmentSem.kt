package com.syrous.ycceyearbook.ui.semester

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentSemesterBinding
import com.syrous.ycceyearbook.model.Subject
import com.syrous.ycceyearbook.ui.home.Department
import com.syrous.ycceyearbook.util.DEPARTMENT_OBJECT
import timber.log.Timber
import javax.inject.Inject

class FragmentSem : Fragment() {

    @Inject
    lateinit var viewModel: SemVM

    private lateinit var _binding: FragmentSemesterBinding

    private lateinit var adapterList: MutableList<SemAdapter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        enteringTransitionForFragment()
        _binding = FragmentSemesterBinding.inflate(layoutInflater, container, false)

        val sharedView = _binding.departmentNameText

        sharedView.viewTreeObserver.addOnPreDrawListener{
            requireActivity().startPostponedEnterTransition()
            return@addOnPreDrawListener true
        }
        return _binding.root
    }

    private fun enteringTransitionForFragment() {
        enterTransition = MaterialFadeThrough().apply {
            addTarget(R.id.sem_recycler)
        }
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as YearBookApplication).appComponent.semComponent()
            .create("ct").inject(this@FragmentSem)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupThemeOfScreen()
        val adapter = setupAdapter()
        setupSemRecyclerView(adapter)
        viewModel.loadListOfSubjects(viewLifecycleOwner, "ct")
        viewModel.subjectList.observe(viewLifecycleOwner) {semSubModel ->
           viewModel.loadListOfSemestersFromLocal("ct").observe(viewLifecycleOwner) {
               for(i in it.indices) {
                   adapterList[i].submitList(semSubModel[i])
               }
           }
        }
    }

    private fun setupSemRecyclerView(mergeAdapter: ConcatAdapter) {
        _binding.semRecycler.apply {
            adapter = mergeAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupAdapter(): ConcatAdapter {
        adapterList = mutableListOf()
        for(i in 0 until 8) {
            val adapter = SemAdapter( RedirectClickHandler(), i, ::showHideSubjects)
            adapterList.add(adapter)
            Timber.d("adapter list : $adapterList")
        }
        return ConcatAdapter(adapterList.toList())
    }

    private fun showHideSubjects(sem: Int, index: Int) = viewModel.toggleChildVisibility(viewLifecycleOwner, "ct", sem, index)

    private fun setupThemeOfScreen() {
        val department = arguments?.get(DEPARTMENT_OBJECT) as Department
        _binding.semToolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), department.backgroundColor))
        _binding.departmentNameText.setBackgroundColor(ContextCompat.getColor(requireContext(), department.backgroundColor))
        _binding.departmentNameText.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(requireContext(), department.largeDrawableId),null,null)
    }

    inner class RedirectClickHandler {
        fun clickListener (subject: Subject) {
            Toast.makeText(requireContext(), "${subject.course} is selected !!!!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(FragmentSemDirections.actionFragmentSemToFragmentPaperAndResource())
        }
    }
}
