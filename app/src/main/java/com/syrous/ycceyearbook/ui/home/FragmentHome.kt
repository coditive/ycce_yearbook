package com.syrous.ycceyearbook.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.databinding.FragmentHomeBinding
import com.syrous.ycceyearbook.flux.Presenter
import com.syrous.ycceyearbook.model.Department
import com.syrous.ycceyearbook.model.User
import com.syrous.ycceyearbook.presenter.HomePresenter
import com.syrous.ycceyearbook.presenter.HomeView
import com.syrous.ycceyearbook.ui.BaseFragment
import com.syrous.ycceyearbook.util.getDepartmentList
import com.syrous.ycceyearbook.util.getOtherFeatureList
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow


const val INSET_TYPE_KEY = "inset_type"
const val INSET = "inset"

class FragmentHome : BaseFragment(), HomeView {
    private lateinit var _binding: FragmentHomeBinding
    private lateinit var departmentGroupAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var otherFeaturesGroupAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var departmentGroupLayoutManager: GridLayoutManager
    private lateinit var otherFeaturesLayoutManager: LinearLayoutManager
    private val departmentSection = Section(HeaderItem(R.string.department_title))
    private lateinit var departmentList: MutableList<Department>
    override var presenter: Presenter = HomePresenter(this)
    override val coroutineScope: CoroutineScope
        get() = viewLifecycleOwner.lifecycle.coroutineScope
    private val _departmentClicks = MutableSharedFlow<Department>()
    override val departmentClicks: SharedFlow<Department>
        get() = _departmentClicks

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        departmentList = getDepartmentList().toMutableList()
        departmentGroupAdapter = GroupAdapter()
        otherFeaturesGroupAdapter = GroupAdapter()
        departmentGroupAdapter.apply {
            spanCount = 12
        }
        departmentGroupLayoutManager = GridLayoutManager(requireContext(),
            departmentGroupAdapter.spanCount).apply {
            spanSizeLookup = departmentGroupAdapter.spanSizeLookup
        }

        _binding.departmentRecycler.apply {
            adapter = departmentGroupAdapter
            ItemTouchHelper(touchCallback).attachToRecyclerView(this)
            layoutManager = departmentGroupLayoutManager
        }

        otherFeaturesLayoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        _binding.otherFeaturesRecycler.apply {
            adapter = otherFeaturesGroupAdapter
            layoutManager = otherFeaturesLayoutManager
        }

        recreateDepartmentAdapter()
        recreateOtherFeatureAdapter()
    }

    private fun recreateDepartmentAdapter() {
        departmentGroupAdapter.clear()
        val groups = createDepartmentGroup()
        departmentGroupAdapter.update(groups)
    }

    private fun createDepartmentGroup(): List<Group> = mutableListOf<Group>().apply {
        this += departmentSection.apply {
            clear()
            add(makeDepartmentColumnGroup(departmentList))
        }
    }

    private fun recreateOtherFeatureAdapter() {
        otherFeaturesGroupAdapter.clear()
        val featureGroup = createOtherFeatureGroup()
        otherFeaturesGroupAdapter.update(featureGroup)
    }

    private fun createOtherFeatureGroup(): List<Group> = mutableListOf<Group>().apply {
        this += Section().apply {
            val featureList = getOtherFeatureList()
            for(feature in featureList) {
                add(OtherFeatureItem(feature, requireContext()))
            }
        }
    }

    private fun makeDepartmentColumnGroup(departmentList: List<Department>): ColumnGroup{
        val columnItems = ArrayList<DepartmentItem>()
        for(dep in departmentList) {
            columnItems += DepartmentItem(dep, requireContext(),
                _departmentClicks, viewLifecycleOwner.lifecycle.coroutineScope)
        }
        return ColumnGroup(columnItems)
    }

    private val touchCallback: SwipeTouchCallback by lazy {
        object: SwipeTouchCallback() {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //NO-OP
            }
        }
    }

    override fun displayUserName(user: User) {
        _binding.userNameTextView.text = "Hello, \n${user.name}!!"
    }

    override fun displayProfilePic(user: User) {
        Glide.with(this).load(user.profilePhotoUrl).into(_binding.profileView)
    }

}

