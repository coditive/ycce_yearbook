package com.syrous.ycceyearbook.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.databinding.FragmentHomeBackBinding

class FragmentHome : Fragment() {

    private lateinit var _binding: FragmentHomeBackBinding

    private lateinit var departmentList: List<Department>

    private lateinit var otherFeatureList: List<OtherFeature>

    private val spaceInPixels = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBackBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       departmentList = setupDepartmentList()
        otherFeatureList = setupOtherFeatureList()
        setupRecyclerViewForDepartment()
    }

    private fun setupOtherFeatureList(): List<OtherFeature> {
        val otherFeature = mutableListOf<OtherFeature>()

        return otherFeatureList
    }

    private fun setupDepartmentList(): List<Department> {
        val department = mutableListOf<Department>()

        department.add(Department("CT", R.drawable.ct_art, R.color.ct_back, R.color.ct_border,0))
        department.add(Department("IT", R.drawable.it_art, R.color.it_back, R.color.it_border,1))
        department.add(Department("ME", R.drawable.me_art, R.color.me_back, R.color.me_border,2))
        department.add(Department("CV", R.drawable.cv_art, R.color.cv_back, R.color.cv_border,3))
        department.add(Department("EE", R.drawable.ee_art, R.color.ee_back, R.color.ee_border,4))
        department.add(Department("EL", R.drawable.el_art, R.color.el_back, R.color.el_border,5))
        department.add(Department("ETC", R.drawable.etc_art, R.color.etc_back, R.color.etc_border,6))
        department.add(Department("FY", R.drawable.fy_art, R.color.fy_back, R.color.fy_border,7))

        return department
    }

    private fun setupRecyclerViewForDepartment() {
        _binding.fragmentHomeFrontPage.departmentRecycler.apply {
            adapter = DepartmentAdapter(departmentList)
            layoutManager = GridLayoutManager(requireContext(), 2)
//            addItemDecoration(object : RecyclerView.ItemDecoration() {
//                override fun getItemOffsets(
//                    outRect: Rect,
//                    view: View,
//                    parent: RecyclerView,
//                    state: RecyclerView.State
//                ) {
//                    outRect.left = spaceInPixels
//                    outRect.right = spaceInPixels
//                    outRect.bottom = spaceInPixels
//                    // Add top margin only for the first item to avoid double space between items
//                    if (parent.getChildLayoutPosition(view) == 0) {
//                        outRect.top = spaceInPixels;
//                    } else {
//                        outRect.top = 0;
//                    }
//                }
//            })
        }
    }
}