package com.example.androidfinalproject.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.search.Location

class LocationRecycleAdapter(private var locationList:List<Location>,private var activity: FragmentActivity?):RecyclerView.Adapter<LocationViewHolder>(){
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
    }

}

class LocationViewHolder(v:View):RecyclerView.ViewHolder(v) {
    val locationName = v.findViewById<TextView>(R.id.location_name)
}
