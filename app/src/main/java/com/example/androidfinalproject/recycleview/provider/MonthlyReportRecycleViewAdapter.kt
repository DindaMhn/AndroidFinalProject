package com.example.androidfinalproject.recycleview.provider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.home.AssetMonthlyView
import kotlinx.android.synthetic.main.item_detail_report_mothly_layout.view.*

class MonthlyReportRecycleViewAdapter (
    val reportMonthly: List<AssetMonthlyView>,
    val getActivity: FragmentActivity?
):RecyclerView.Adapter<MonthlyReportViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthlyReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detail_report_mothly_layout, parent,false)

        return MonthlyReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reportMonthly.size
    }

    override fun onBindViewHolder(holder: MonthlyReportViewHolder, position: Int) {
        holder.monthly.text = reportMonthly[position].months
        holder.totalParked.text = reportMonthly[position].total_parked
        holder.totalRevenue.text = "Rp. ${reportMonthly[position].total_revenue}"
    }

}

class MonthlyReportViewHolder(v: View): RecyclerView.ViewHolder(v){
    val monthly = v.findViewById<TextView>(R.id.item_monthly_data_report_view)
    val totalParked = v.findViewById<TextView>(R.id.item_monthly_data_total_parked_report_view)
    val totalRevenue = v.findViewById<TextView>(R.id.item_monthly_revenue_report_view)
}