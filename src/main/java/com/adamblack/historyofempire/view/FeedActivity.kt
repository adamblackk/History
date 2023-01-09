package com.adamblack.historyofempire.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.adamblack.historyofempire.R
import com.adamblack.historyofempire.databinding.ActivityFeedBinding
import com.adamblack.historyofempire.viewmodel.FeedViewModel


class FeedActivity : AppCompatActivity() {
    private lateinit var viewModel: FeedViewModel
    private lateinit var binding: ActivityFeedBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController=navHostFragment.navController
        setupWithNavController(binding.bottomNavigationView,navController)

        navHostFragment.findNavController().run {
        binding.toolbar.setupWithNavController(this, AppBarConfiguration(graph))
        }


    }
}


