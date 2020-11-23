package com.syrous.ycceyearbook.ui.notices

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentNoticeBinding
import com.syrous.ycceyearbook.model.Notification
import javax.inject.Inject

class FragmentNotices : Fragment() {

    @Inject
    lateinit var viewModel: NotificationVM

    private lateinit var binding: FragmentNoticeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoticeBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as YearBookApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noticeAdapter = NotificationAdapter(ClickHandler())

        viewModel.noticeMsg.observe(viewLifecycleOwner) {
            noticeAdapter.submitList(it)
        }

        binding.noticeRecycler.apply {
            adapter = noticeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    inner class ClickHandler() {
        fun onClick(notice: Notification) {
            Toast.makeText(requireContext(), "Notice : $notice", Toast.LENGTH_SHORT).show()
        }
    }
}