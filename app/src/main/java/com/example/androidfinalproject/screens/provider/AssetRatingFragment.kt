package com.example.androidfinalproject.screens.provider

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.asset.AssetViewModel
import com.example.androidfinalproject.recycleview.provider.AssetRatingRecycleAdapter
import kotlinx.android.synthetic.main.fragment_asset_rating.*
import javax.inject.Inject


class AssetRatingFragment : Fragment() {

    private lateinit var assetRatingRecycleAdapter: AssetRatingRecycleAdapter
    @Inject lateinit var assetViewModel: AssetViewModel
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asset_rating, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycle_view_asset_rating.layoutManager=LinearLayoutManager(activity)
        var providerId = sharedPreferences?.getString("ID_PROVIDER","").toString()
        assetViewModel.getRatingAsset(providerId)
        assetViewModel.assetRating.observe(viewLifecycleOwner, Observer {
            assetRatingRecycleAdapter = AssetRatingRecycleAdapter(it,activity)
            recycle_view_asset_rating.adapter=assetRatingRecycleAdapter
        })
    }


}