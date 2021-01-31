package com.syrous.ycceyearbook.ui.home

import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.YearBookApplication
import com.syrous.ycceyearbook.databinding.FragmentHomeBackBinding
import com.syrous.ycceyearbook.model.User
import com.syrous.ycceyearbook.util.Truss
import com.syrous.ycceyearbook.util.getDepartmentList
import com.syrous.ycceyearbook.util.getOtherFeatureList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class FragmentHome : Fragment() {

    private lateinit var _binding: FragmentHomeBackBinding

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
        (requireActivity().application as YearBookApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }
    }

    private fun setupRecyclerViewForDepartment(departmentList: List<Department>) {
        _binding.fragmentHomeFrontPage.departmentRecycler.apply {
            adapter = DepartmentAdapter(departmentList, DepartmentClickHandler())
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    inner class DepartmentClickHandler {
        fun clickListener(index: Int, view: View) {
            TODO("Navigate")
//            when(index) {
//                0 -> {
//                    Toast.makeText(requireContext(), "CT Selected", Toast.LENGTH_SHORT).show()
//                    intent.putExtra(DEPARTMENT_OBJECT, departmentList[index])
//                    val cardViewPair = Pair.create(view, "shared_element")
//                    val textViewPair = Pair.create(view.findViewById(R.id.department_name) as View, "shared_textview")
//                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), cardViewPair, textViewPair)
//                    startActivity(intent, options.toBundle())
//                }
//            }
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

