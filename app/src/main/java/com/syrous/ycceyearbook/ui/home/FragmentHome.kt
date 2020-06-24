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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBackBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      val departmentList = getDepartmentList()
        setupRecyclerViewForDepartment(departmentList)
       val otherFeatureList = getOtherFeatureList()
        setupRecyclerViewForOtherFeatures(otherFeatureList)
    }

    private fun setupRecyclerViewForOtherFeatures(otherFeatureList: List<OtherFeature>) {
        _binding.fragmentHomeFrontPage.otherFeaturesRecycler.apply {
            adapter = OtherFeaturesAdapter(otherFeatureList)
            layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        }
    }

    private fun getOtherFeatureList(): List<OtherFeature> {
        val otherFeature = mutableListOf<OtherFeature>()

        otherFeature.add(OtherFeature("YCCE", R.drawable.home, "www.ycce.edu"))
        otherFeature.add(OtherFeature("Moodle", R.drawable.moodle_icon, "https://ycce.in/"))
        otherFeature.add(OtherFeature("Upload Paper", R.drawable.upload_icon, "1231"))
        otherFeature.add(OtherFeature("Fees Payment", R.drawable.pay_icon, "www.ycce.edu"))
        return otherFeature
    }

    private fun getDepartmentList(): List<Department> {
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

    private fun setupRecyclerViewForDepartment(departmentList: List<Department>) {
        _binding.fragmentHomeFrontPage.departmentRecycler.apply {
            adapter = DepartmentAdapter(departmentList)
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}