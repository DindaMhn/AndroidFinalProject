package com.example.androidfinalproject.screens.provider

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.asset.AssetViewModel
import com.example.androidfinalproject.recycleview.provider.ListAssetProviderRecycleAdapter
import com.example.androidfinalproject.recycleview.user.HistoryUserRecycleAdapter
import kotlinx.android.synthetic.main.fragment_list_asset_provider.*
import javax.inject.Inject


class ListAssetProvider : Fragment() {
    private lateinit var listAssetProviderRecycleAdapter: ListAssetProviderRecycleAdapter
    @Inject lateinit var listAssetViewModel: AssetViewModel
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
        return inflater.inflate(R.layout.fragment_list_asset_provider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycle_view_list_asset.layoutManager=LinearLayoutManager(activity)
        val id = sharedPreferences?.getString("ID_PROVIDER","")
        println("iniidprovider ${id}")
        id?.let { listAssetViewModel.getAssetListByProviderID(it) }
        listAssetViewModel.assetList.observe(viewLifecycleOwner, Observer {
            listAssetProviderRecycleAdapter = ListAssetProviderRecycleAdapter(it, activity)

            recycle_view_list_asset.adapter=listAssetProviderRecycleAdapter
        })
    }


}