package com.kinzlstanislav.topcontributors.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kinzlstanislav.topcontributors.R
import com.kinzlstanislav.topcontributors.feature.list.viewmodel.ContributorsListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val contributorsListViewModel: ContributorsListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchDataOnAppStart()
    }

    // Sometimes it is preferable to load data on the start of the application so
    // there is no need to wait at further places in the app for the data to fetch.
    private fun fetchDataOnAppStart() {
        contributorsListViewModel.getRubyContributors()
    }
}