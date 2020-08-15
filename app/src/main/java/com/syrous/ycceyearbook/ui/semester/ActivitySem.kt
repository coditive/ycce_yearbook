package com.syrous.ycceyearbook.ui.semester

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.util.DEPARTMENT_OBJECT

class ActivitySem : AppCompatActivity(R.layout.activity_sem) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        postponeEnterTransition()

        navController = findNavController(R.id.sem_nav_host)

        val department = intent.getSerializableExtra(DEPARTMENT_OBJECT)

        val args = bundleOf(DEPARTMENT_OBJECT to department)

        navController.setGraph(R.navigation.nav_graph3, args)
    }
}