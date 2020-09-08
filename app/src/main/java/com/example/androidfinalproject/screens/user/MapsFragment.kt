package com.example.androidfinalproject.screens.user

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.account.UserViewModel
import com.example.androidfinalproject.user.search.LocationViewModel
import com.google.android.gms.location.LocationServices

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
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)

        checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION, "location",FINE_LOCATION_RQ)

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
    fun checkPermission(permission:String, name : String, requestCode :Int){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(context as Activity,permission) == PackageManager.PERMISSION_GRANTED->{
                    Toast.makeText(activity,"$name permission granted", Toast.LENGTH_SHORT).show()
                }
                shouldShowRequestPermissionRationale(permission)-> showDialog(permission,name,requestCode)
                else->{

                    ActivityCompat.requestPermissions(context as Activity, arrayOf(permission),requestCode)
                }
            }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        fun innerCheck(name: String){
            if(grantResults.isEmpty()||grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(context,"$name permission refused", Toast.LENGTH_SHORT).show()
            }else{

                Toast.makeText(context,"$name permission granted", Toast.LENGTH_SHORT).show()

            }
        }
        when(requestCode){
            FINE_LOCATION_RQ -> innerCheck("location")
        }
    }
    private fun showDialog(permission:String, name : String, requestCode :Int){
        val builder = AlertDialog.Builder(context as Activity)
        builder.apply {
            setMessage("Permission to access your $name is required")
            setTitle("Permission Required")
            setPositiveButton("OK"){ dialog, which -> ActivityCompat.requestPermissions(context as Activity, arrayOf(permission),requestCode)
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}