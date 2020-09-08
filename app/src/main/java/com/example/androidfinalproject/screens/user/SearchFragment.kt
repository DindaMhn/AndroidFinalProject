package com.example.androidfinalproject.screens.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.adapter.LocationRecycleAdapter
import com.example.androidfinalproject.user.search.LocationViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment() {
    @Inject
    lateinit var locationViewModel: LocationViewModel
    lateinit var adapter:LocationRecycleAdapter
//    val locationViewModel by activityViewModels<LocationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationRecycleView.layoutManager = LinearLayoutManager(activity)
        locationViewModel.listLocationData.observe(viewLifecycleOwner, Observer {
            adapter = LocationRecycleAdapter(it,activity)
            locationRecycleView.adapter = adapter
        })
        locationViewModel.getAssetLocation()
    }
}