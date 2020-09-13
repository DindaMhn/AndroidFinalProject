package com.example.androidfinalproject.recycleview.provider

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.asset.AssetViewModel
import com.example.androidfinalproject.provider.home.Asset
import com.example.androidfinalproject.provider.home.AssetView
import com.example.androidfinalproject.recycleview.user.PaymentViewHolder
import kotlinx.android.synthetic.main.item_list_asset_provider_layout.view.*
import javax.inject.Inject

class ListAssetProviderRecycleAdapter(
    val assetList: List<AssetView>,
    val getActivity: FragmentActivity?
) : RecyclerView.Adapter<AssetViewHolder>() {
    @Inject
    lateinit var assetViewModel: AssetViewModel
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
        val id_asset = bundleOf(
            Pair("id_asset", assetList[position].id)
            , Pair("asset_name", assetList[position].asset_name)
        )
        val longitude = assetList[position].longitude
        val latitude = assetList[position].latitude
        val assetLocation = "${latitude} ,${longitude}"
        holder.assetName.text = assetList[position].asset_name
        holder.location.text = assetLocation
        if (assetList[position].status == "A") {
            holder.assetName.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_baseline_location_on_24,
                0,
                0,
                0
            )
            holder.status.text = "Active"
            holder.status.setTextColor(Color.BLUE)
            println(id_asset)
            holder.itemView.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_global_monthlyReportProviderfragment, id_asset)
            }
        } else if (assetList[position].status == "I") {
            holder.assetName.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_baseline_location_on_24,
                0,
                0,
                0
            )
            holder.status.text = "Waiting for Confirmation"
            holder.status.setTextColor(Color.RED)
        } else {
            holder.assetName.text = "No Asset"
            holder.status.visibility = View.GONE
        }


    }
}

class AssetViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val assetName = v.findViewById<TextView>(R.id.item_asset_name_list)
    val location = v.findViewById<TextView>(R.id.item_location_lat_long_asset_list)
    val status = v.findViewById<TextView>(R.id.status_asset)
}