package com.example.androidfinalproject.screens.user

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemServiceName
import androidx.lifecycle.Observer
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.account.UserViewModel
import com.example.androidfinalproject.user.search.LocationViewModel
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import javax.inject.Inject

class MapsFragment : Fragment() {
    @Inject
    lateinit var locationViewModel: LocationViewModel
    val FINE_LOCATION_RQ = 101
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    var devLong: Double = 0.00
    var devLat:Double = 0.00


    private val callback = OnMapReadyCallback { googleMap ->
        locationViewModel.listLocationData.observe(viewLifecycleOwner, Observer {
            println("masuk map")
            println(it.size)
            for (i in 0 until it.size){
                googleMap.addMarker(MarkerOptions().position(
                    LatLng(it[i].latitude.toDouble(),it[i].longitude.toDouble())
                ).title(it[i].asset_name))
            }
        })
        locationViewModel.getAssetLocation()
        getLastLocation()
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity as Activity)

//        checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, "location",FINE_LOCATION_RQ)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun getLastLocation(){
        if(checkPermission()){
            if(isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener{task->
                    var deviceLocation = task.result
                    if(deviceLocation == null){
                        getNewLocation()
                    } else{
                        devLat = deviceLocation!!.latitude
                        devLong = deviceLocation!!.longitude
                    }
                }
            }else{
                Toast.makeText(context,"Please Enable Your Location Service",Toast.LENGTH_SHORT).show()
            }
        }else{
            requestPermission()
        }
    }

    private fun getNewLocation(){
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        checkPermission()
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }
    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            var lastLocation = p0.lastLocation
            devLat = lastLocation!!.latitude
            devLong= lastLocation!!.longitude
        }
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

    private fun requestPermission(){
        ActivityCompat.requestPermissions(activity as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),FINE_LOCATION_RQ)
    }

    private fun isLocationEnabled():Boolean{
        var locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
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

//    fun checkPermission(permission:String, name : String, requestCode :Int){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            when{
//                ContextCompat.checkSelfPermission(context as Activity,permission) == PackageManager.PERMISSION_GRANTED->{
//                    Toast.makeText(activity,"$name permission granted", Toast.LENGTH_SHORT).show()
//                }
//                shouldShowRequestPermissionRationale(permission)-> showDialog(permission,name,requestCode)
//                else->{
//
//                    ActivityCompat.requestPermissions(context as Activity, arrayOf(permission),requestCode)
//                }
//            }
//        }
//    }
//
//
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        fun innerCheck(name: String){
//            if(grantResults.isEmpty()||grantResults[0] != PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(context,"$name permission refused", Toast.LENGTH_SHORT).show()
//            }else{
//
//                Toast.makeText(context,"$name permission granted", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//        when(requestCode){
//            FINE_LOCATION_RQ -> innerCheck("location")
//        }
//    }
//    private fun showDialog(permission:String, name : String, requestCode :Int){
//        val builder = AlertDialog.Builder(context as Activity)
//        builder.apply {
//            setMessage("Permission to access your $name is required")
//            setTitle("Permission Required")
//            setPositiveButton("OK"){ dialog, which -> ActivityCompat.requestPermissions(context as Activity, arrayOf(permission),requestCode)
//            }
//            val dialog = builder.create()
//            dialog.show()
//        }
//    }
}