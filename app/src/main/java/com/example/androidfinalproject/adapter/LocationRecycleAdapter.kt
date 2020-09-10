package com.example.androidfinalproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.search.Location

class LocationRecycleAdapter(
    private var locationList: List<Location>,
    private var activity: FragmentActivity?
):RecyclerView.Adapter<LocationViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return LocationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.locationName.text =locationList[position].asset_name
        holder.carCap.text = locationList[position].car_capacity_available
        holder.bicycleCap.text = locationList[position].bicycle_capacity_available
        println("BICYCLE"+locationList[position].bicycle_capacity_available)
        holder.motorcycleCap.text = locationList[position].motorcycle_capacity_available
        holder.itemView.setOnClickListener {
            val bundle = bundleOf(
                Pair("id", locationList[position].id)
            )
            Navigation.findNavController(it)
                .navigate(R.id.action_searchFragment_to_detailAssetFragment,bundle)
        }
    }
}

class LocationViewHolder(v:View):RecyclerView.ViewHolder(v) {
    val locationName = v.findViewById<TextView>(R.id.asset_name)
    val carCap = v.findViewById<TextView>(R.id.carCap)
    val bicycleCap = v.findViewById<TextView>(R.id.bicycleCap)
    val motorcycleCap = v.findViewById<TextView>(R.id.motorCap)
}
