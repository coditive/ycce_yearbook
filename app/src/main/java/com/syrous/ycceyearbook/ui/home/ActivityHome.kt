package com.syrous.ycceyearbook.ui.home

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.syrous.ycceyearbook.R
import com.syrous.ycceyearbook.ui.home.bottom_nav.OnItemSelectedListener
import kotlinx.android.synthetic.main.activity_home.*

class ActivityHome : AppCompatActivity(R.layout.activity_home) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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