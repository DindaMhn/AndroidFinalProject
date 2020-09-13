package com.example.androidfinalproject.recycleview.provider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.home.AssetRating
import kotlinx.android.synthetic.main.item_asset_rating.view.*

class AssetRatingRecycleAdapter (
    val assetRating: List<AssetRating>,
    val getActivity: FragmentActivity?
):RecyclerView.Adapter<AssetRatingViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetRatingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asset_rating, parent,false)
        return AssetRatingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return assetRating.size
    }

    override fun onBindViewHolder(holder: AssetRatingViewHolder, position: Int) {
        holder.assetName.text = assetRating[position].asset_name
        if (assetRating[position].rating!=""){
            holder.ratingBar.rating = assetRating[position].rating.toFloat()
        } else {
            holder.ratingBar.rating = 0.toFloat()
        }
        holder.comment.text = assetRating[position].comment

    }

}

class AssetRatingViewHolder(v: View):RecyclerView.ViewHolder(v){
    var assetName = v.findViewById<TextView>(R.id.item_asset_rating_list)
    var ratingBar = v.findViewById<RatingBar>(R.id.rating_bar_asset_list)
    var comment = v.findViewById<TextView>(R.id.item_comment_rating_list)
}
