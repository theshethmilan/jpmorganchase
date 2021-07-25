package com.milan.jpmorganchase.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.milan.jpmorganchase.database.MyRoomDatabase
import com.milan.jpmorganchase.ui.activity.ActivityMain
import javax.inject.Inject

abstract class BaseFragment<dataBinding : ViewDataBinding> : Fragment() {
    private lateinit var dataBinding: dataBinding
    protected lateinit var controller: NavController
    protected lateinit var activityMain: ActivityMain

    @Inject
    lateinit var gson: Gson
    @Inject
    lateinit var myRoomDatabase: MyRoomDatabase
    abstract fun layoutId(): Int
    protected abstract fun initView(view: View)
    fun getDataBinding() = dataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMain = activity as ActivityMain
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = Navigation.findNavController(view)
        initView(view)
    }
}