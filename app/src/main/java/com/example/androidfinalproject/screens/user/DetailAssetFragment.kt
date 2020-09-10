package com.example.androidfinalproject.screens.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.adapter.LocationRecycleAdapter
import com.example.androidfinalproject.user.search.LocationViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_detail_asset.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.text.DecimalFormat
import javax.inject.Inject

class DetailAssetFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var locationViewModel: LocationViewModel
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
        return inflater.inflate(R.layout.fragment_detail_asset, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationViewModel.detailLocationData.observe(viewLifecycleOwner, Observer {
            assetNameDetail.text = it.asset_name
            if (it != null) {
                distanceDetail.text = "Distance : " + CalculationByDistance(
                    LatLng(it.latitude.toDouble(), it.longitude.toDouble()),
                    LatLng(
                        sharedPreferences?.getString(
                            "device_latitude", ""
                        )!!.toDouble(), sharedPreferences?.getString(
                            "device_longitude", ""
                        )!!.toDouble()
                    )
                ).toString()
            }
        })
        println("detail")
        println(arguments?.getString("id"))
        locationViewModel.getDetail(arguments?.getString("id")!!)
        bookAssetButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            bookAssetButton -> {
                val bundle = bundleOf(Pair("asset_id", arguments?.getString("id")))
                v?.findNavController()
                    ?.navigate(R.id.action_detailAssetFragment_to_bookingTicketFragment, bundle)
            }
        }
    }

    fun CalculationByDistance(StartP: LatLng, EndP: LatLng): Double {
        val Radius = 6371 // radius of earth in Km
        val lat1 = StartP.latitude
        val lat2 = EndP.latitude
        val lon1 = StartP.longitude
        val lon2 = EndP.longitude
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = (Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + (Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2)))
        val c = 2 * Math.asin(Math.sqrt(a))
        val valueResult = Radius * c
        val km = valueResult / 1
        val newFormat = DecimalFormat("####")
        val kmInDec: Int = Integer.valueOf(newFormat.format(km))
        val meter = valueResult % 1000
        val meterInDec: Int = Integer.valueOf(newFormat.format(meter))
        Log.i(
            "Radius Value", "" + valueResult + "   KM  " + kmInDec
                    + " Meter   " + meterInDec
        )
        return Radius * c
    }

}