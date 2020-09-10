package com.example.androidfinalproject.recycleview.provider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.home.AssetDailyView
import kotlinx.android.synthetic.main.item_detail_report_daily_layout.view.*

class DailyReportRecycleAdapter (
    val reportDaily: List<AssetDailyView>,
    val getActivity: FragmentActivity?
):RecyclerView.Adapter<DailyReportViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detail_report_daily_layout, parent,false)

        return DailyReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reportDaily.size
    }

    override fun onBindViewHolder(holder: DailyReportViewHolder, position: Int) {
        holder.date.text = reportDaily[position].date
        holder.totalParked.text = reportDaily[position].total_parked
        holder.totalRevenue.text = "Rp. ${reportDaily[position].total_revenue}"
    }


}

class DailyReportViewHolder(v:View):RecyclerView.ViewHolder(v){
    val date = v.findViewById<TextView>(R.id.item_daily_data_report_view)
    val totalParked = v.findViewById<TextView>(R.id.item_daily_data_total_parked_report_view)
    val totalRevenue = v.findViewById<TextView>(R.id.item_daily_revenue_report_view)
}