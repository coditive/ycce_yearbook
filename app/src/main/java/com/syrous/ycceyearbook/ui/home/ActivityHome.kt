package com.syrous.ycceyearbook.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.ui.home.bottom_nav.OnItemSelectedListener
import kotlinx.android.synthetic.main.activity_home.*

class ActivityHome : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        // Enable Activity Transitions. Optionally enable Activity transitions in your
        // theme with <item name=”android:windowActivityTransitions”>true</item>.
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        // Attach a callback used to capture the shared elements from this Activity to be used
        // by the container transform transition
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navController = findNavController(R.id.home_nav_host)
        bottomBar.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelect(pos: Int): Boolean {
                when(pos){
                    0 -> navController.navigate(R.id.fragmentHome)
                    1 -> navController.navigate(R.id.fragmentRecent)
                    2 -> navController.navigate(R.id.fragmentNotices)
                    3 -> navController.navigate(R.id.fragmentMore)
                }
                return true
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }
}