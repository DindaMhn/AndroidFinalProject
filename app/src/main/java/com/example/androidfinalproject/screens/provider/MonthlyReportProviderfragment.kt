package com.example.androidfinalproject.screens.provider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.asset.AssetViewModel
import com.example.androidfinalproject.recycleview.provider.MonthlyReportRecycleViewAdapter
import kotlinx.android.synthetic.main.fragment_monthly_report_providerfragment.*
import javax.inject.Inject


class MonthlyReportProviderfragment : Fragment(),View.OnClickListener {
    private lateinit var monthlyReportRecycleViewAdapter: MonthlyReportRecycleViewAdapter
    @Inject lateinit var assetViewModel: AssetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monthly_report_providerfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("id_asset","")
        val assetName = arguments?.getString("asset_name","")
        mothly_header_asset_name.text = assetName
        id?.let { assetViewModel.getReportAssetMonthly(it) }
        recycle_view_montly_report_asset.layoutManager=LinearLayoutManager(activity)
        assetViewModel.reportMonthly.observe(viewLifecycleOwner, Observer {
            monthlyReportRecycleViewAdapter = MonthlyReportRecycleViewAdapter(it,activity)
            recycle_view_montly_report_asset.adapter = monthlyReportRecycleViewAdapter
        })

        button_to_daily_report_monthly.setOnClickListener(this)
        button_to_monthly_report_monthly.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()

    }
    override fun onClick(v: View?) {
        when (v) {
            button_to_daily_report_monthly -> {
                val id_asset = bundleOf(
                    Pair("id_asset", arguments?.getString("id_asset",""))
                    ,Pair("asset_name", arguments?.getString("asset_name",""))
                )
                v?.findNavController()
                    ?.navigate(R.id.action_global_dailyReportProviderFragment, id_asset)
            }
            button_to_monthly_report_monthly -> {

            }
        }
    }


}