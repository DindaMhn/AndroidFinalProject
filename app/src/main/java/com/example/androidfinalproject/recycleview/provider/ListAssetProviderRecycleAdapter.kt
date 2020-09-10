package com.example.androidfinalproject.recycleview.provider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.asset.AssetViewModel
import com.example.androidfinalproject.provider.home.Asset
import com.example.androidfinalproject.recycleview.user.PaymentViewHolder
import kotlinx.android.synthetic.main.item_list_asset_provider_layout.view.*
import javax.inject.Inject

class ListAssetProviderRecycleAdapter(
    val assetList: List<Asset>,
    val getActivity: FragmentActivity?
):RecyclerView.Adapter<AssetViewHolder>() {
    @Inject lateinit var assetViewModel: AssetViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_asset_provider_layout, parent, false)
        (getActivity?.applicationContext as MyApplication).applicationComponent.inject(this)
        return AssetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return assetList.size
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        val longitude = assetList[position].longitude
        val latitude = assetList[position].latitude
        val assetLocation = "${latitude} ,${longitude}"
        holder.assetName.text = assetList[position].asset_name
        holder.location.text = assetLocation
    }
}

class AssetViewHolder(v: View): RecyclerView.ViewHolder(v){
    val assetName = v.findViewById<TextView>(R.id.item_asset_name_list)
    val location = v.findViewById<TextView>(R.id.item_location_lat_long_asset_list)
}