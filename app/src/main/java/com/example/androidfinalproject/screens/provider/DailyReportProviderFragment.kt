package com.example.androidfinalproject.screens.provider

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.asset.AssetViewModel
import com.example.androidfinalproject.recycleview.provider.DailyReportRecycleAdapter
import com.example.androidfinalproject.recycleview.provider.MonthlyReportRecycleViewAdapter
import kotlinx.android.synthetic.main.fragment_add_asset.*
import kotlinx.android.synthetic.main.fragment_daily_report_provider.*
import kotlinx.android.synthetic.main.fragment_monthly_report_providerfragment.*
import javax.inject.Inject


class DailyReportProviderFragment : Fragment(),View.OnClickListener {
    private lateinit var dailyReportRecycleAdapter: DailyReportRecycleAdapter

    @Inject
    lateinit var assetViewModel: AssetViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_report_provider, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = arguments?.getString("id_asset","")
        val assetName = arguments?.getString("asset_name","")
        daily_header_asset_name.text = assetName
        id?.let { assetViewModel.getReportAssetDaily(it) }

        recycle_view_daily_report_asset.layoutManager= LinearLayoutManager(activity)
        assetViewModel.reportDaily.observe(viewLifecycleOwner, Observer {
            dailyReportRecycleAdapter = DailyReportRecycleAdapter(it,activity)
            recycle_view_daily_report_asset.adapter = dailyReportRecycleAdapter
        })

        button_to_daily_report_daily.setOnClickListener(this)
        button_to_monthly_report_daily.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            button_to_daily_report_daily -> {

            }
            button_to_monthly_report_daily -> {
                val id_asset = bundleOf(
                    Pair("id_asset", arguments?.getString("id_asset",""))
                    ,Pair("asset_name", arguments?.getString("asset_name",""))
                )
                v?.findNavController()
                    ?.navigate(R.id.action_global_monthlyReportProviderfragment,id_asset)
            }
        }
    }


}