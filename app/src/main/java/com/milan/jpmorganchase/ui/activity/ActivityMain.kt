package com.milan.jpmorganchase.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.milan.jpmorganchase.R
import com.milan.jpmorganchase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityMain : AppCompatActivity() {
    private lateinit var controller: NavController
    private lateinit var bindingActivityMain: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivityMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingActivityMain.root)
        controller = Navigation.findNavController(this, R.id.hostFragment)
    }

    fun showLoading() {
        bindingActivityMain.progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        bindingActivityMain.progressBar.visibility = View.GONE
    }
}