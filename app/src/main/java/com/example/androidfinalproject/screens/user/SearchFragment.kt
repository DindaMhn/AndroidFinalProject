package com.example.androidfinalproject.screens.user

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.adapter.LocationRecycleAdapter
import com.example.androidfinalproject.user.search.LocationViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment(),OnMapReadyCallback {
    @Inject
    lateinit var locationViewModel: LocationViewModel
    lateinit var adapter:LocationRecycleAdapter
    val FINE_LOCATION_RQ = 101
    lateinit var gmap : GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    var deviceLocation : Location?=null
    var sharedPreferences: SharedPreferences? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()
        map_view.getMapAsync(this)
//    checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, "location",FINE_LOCATION_RQ)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Activity)
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
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationRecycleView.layoutManager = LinearLayoutManager(activity)
        locationViewModel.listLocationData.observe(viewLifecycleOwner, Observer {
            adapter = LocationRecycleAdapter(it,activity)
            locationRecycleView.adapter = adapter
        })
        locationViewModel.getAssetLocation()
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            gmap = it
            getLastLocation()

        }
        locationViewModel.listLocationData.observe(viewLifecycleOwner, Observer {
            for (i in 0 until it.size){
                gmap.addMarker(MarkerOptions().position(
                    LatLng(it[i].latitude.toDouble(),it[i].longitude.toDouble())
                ).title(it[i].asset_name))

            }

        })

    }



    private fun checkPermission():Boolean{
        if(
            ActivityCompat.checkSelfPermission(context as Activity,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(context as Activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == FINE_LOCATION_RQ){
            if(grantResults.isEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Log.d("Debug","You Have The Permission")
            }
        }
    }

    private fun getLastLocation(){
        if(checkPermission()){
            if(isLocationEnabled()){
                fusedLocationClient.lastLocation.addOnCompleteListener{task->
                    var deviceLocation = task.result
                    if(deviceLocation == null){
                        getNewLocation()
                    } else{
                        gmap.isMyLocationEnabled = true
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            // Got last known location. In some rare situations this can be null.
                            // 3
                            if (location != null) {
                                deviceLocation = location
                                val currentLatLng = LatLng(location.latitude, location.longitude)
                                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))

                                    with(sharedPreferences?.edit()) {
                                        this?.putString(
                                            getString(R.string.device_latitude),
                                            location.latitude.toString()
                                        )
                                        this?.putString(
                                            getString(R.string.device_longitude),
                                            location.longitude.toString()
                                        )
                                        this?.commit()
                                    }
                            }
                        }
                    }
                }
            }else{
                Toast.makeText(context,"Please Enable Your Location Service", Toast.LENGTH_SHORT).show()
            }
        }else{
            requestPermission()
        }
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(activity as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),FINE_LOCATION_RQ)
    }

    private fun getNewLocation(){
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        checkPermission()
        fusedLocationClient!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }
    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            var lastLocation = p0.lastLocation
            deviceLocation=lastLocation
        }
    }

    private fun isLocationEnabled():Boolean{
        var locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


}