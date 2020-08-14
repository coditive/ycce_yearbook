package com.syrous.ycceyearbook.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import androidx.lifecycle.observe
import android.os.Bundle
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.model.User
import com.syrous.ycceyearbook.databinding.FragmentHomeBackBinding
import com.syrous.ycceyearbook.ui.semester.ActivitySem
import com.syrous.ycceyearbook.util.DEPARTMENT_OBJECT
import com.syrous.ycceyearbook.util.Truss
import javax.inject.Inject

class FragmentHome : Fragment() {

    private lateinit var _binding: FragmentHomeBackBinding

    @Inject
    lateinit var viewModel: HomeVM

    private lateinit var departmentList: List<Department>

    private lateinit var otherFeatureList: List<OtherFeature>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBackBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as YearBookApplication).appComponent.userComponent().create().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getUserProfile()
        viewModel.userProfile.observe(viewLifecycleOwner) {
            Glide.with(requireContext()).load(it.profilePhotoUrl).into(_binding.profileView)
            setupUserNameOnWelcomeScreen(it)
        }


        departmentList = getDepartmentList()
        setupRecyclerViewForDepartment(departmentList)
        otherFeatureList = getOtherFeatureList()
        setupRecyclerViewForOtherFeatures(otherFeatureList)
    }

    private fun setupUserNameOnWelcomeScreen(user: User) {
      val userName = user.name?.split(" ")
        _binding.welcomeDisplay.text = Truss().pushSpan(AbsoluteSizeSpan(18, true))
            .pushSpan(StyleSpan(Typeface.BOLD))
            .append("YCCE YearBook")
            .popSpan()
            .popSpan()
            .pushSpan(AbsoluteSizeSpan(24, true))
            .append("\n Welcome ${userName?.get(0) ?: "User"}!!")
            .build()

    }

    private fun setupRecyclerViewForOtherFeatures(otherFeatureList: List<OtherFeature>) {
        _binding.fragmentHomeFrontPage.otherFeaturesRecycler.apply {
            adapter = OtherFeaturesAdapter(otherFeatureList, OtherFeatureClickHandler())
            layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)
        }
    }

    private fun getOtherFeatureList(): List<OtherFeature> {
        val otherFeature = mutableListOf<OtherFeature>()
        otherFeature.add(OtherFeature("YCCE", R.drawable.home, "https://www.ycce.edu"))
        otherFeature.add(OtherFeature("Moodle", R.drawable.moodle_icon, "https://ycce.in/"))
        otherFeature.add(OtherFeature("Upload Paper", R.drawable.upload_icon, "1231"))
        otherFeature.add(OtherFeature("Fees Payment", R.drawable.pay_icon, "https://www.ycce.edu"))
        return otherFeature
    }

    private fun getDepartmentList(): List<Department> {
        val department = mutableListOf<Department>()
        department.add(Department("CT", R.drawable.ct_art, R.drawable.ct_art_large, R.color.ct_back, R.color.ct_border,0))
        department.add(Department("IT", R.drawable.it_art, R.drawable.it_art_large, R.color.it_back, R.color.it_border,1))
        department.add(Department("ME", R.drawable.me_art, R.drawable.me_art_large, R.color.me_back, R.color.me_border,2))
        department.add(Department("CV", R.drawable.cv_art, R.drawable.cv_art_large, R.color.cv_back, R.color.cv_border,3))
        department.add(Department("EE", R.drawable.ee_art, R.drawable.ee_art_large, R.color.ee_back, R.color.ee_border,4))
        department.add(Department("EL", R.drawable.el_art, R.drawable.el_art_large, R.color.el_back, R.color.el_border,5))
        department.add(Department("ETC", R.drawable.etc_art, R.drawable.etc_art_large, R.color.etc_back, R.color.etc_border,6))
        department.add(Department("FY", R.drawable.fy_art, R.drawable.fy_art_large, R.color.fy_back, R.color.fy_border,7))
        return department
    }

    private fun setupRecyclerViewForDepartment(departmentList: List<Department>) {
        _binding.fragmentHomeFrontPage.departmentRecycler.apply {
            adapter = DepartmentAdapter(departmentList, DepartmentClickHandler())
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    inner class DepartmentClickHandler {
        fun clickListener(index: Int, view: View) {

            val intent = Intent(requireActivity(), ActivitySem::class.java)

            when(index) {
                0 -> {
                    Toast.makeText(requireContext(), "CT Selected", Toast.LENGTH_SHORT).show()
                    intent.putExtra(DEPARTMENT_OBJECT, departmentList[index])
                    val cardViewPair = Pair.create(view, "shared_element")
                    val textViewPair = Pair.create(view.findViewById(R.id.department_name) as View, "shared_textview")
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), cardViewPair, textViewPair)
                    startActivity(intent, options.toBundle())
                }
            }
        }
    }

    inner class OtherFeatureClickHandler {
        fun clickListener(index: Int) {
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(ContextCompat.getColor(requireContext(), R.color.color_primary))
            builder.setShowTitle(true)

            val customTabIntent = builder.build()

            when(index) {
                0 -> customTabIntent.launchUrl(requireContext(), Uri.parse(otherFeatureList[index].url))
                1 -> customTabIntent.launchUrl(requireContext(), Uri.parse(otherFeatureList[index].url))
            }
        }
    }

}

