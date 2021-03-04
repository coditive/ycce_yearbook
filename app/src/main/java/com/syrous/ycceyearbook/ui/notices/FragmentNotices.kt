package com.syrous.ycceyearbook.ui.notices

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentNoticeBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class FragmentNotices : Fragment() {
    private lateinit var binding: FragmentNoticeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoticeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as YearBookApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        val noticeAdapter = NotificationAdapter(ClickHandler())
//
//
//        binding.noticeRecycler.apply {
//            adapter = noticeAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }
    }
}