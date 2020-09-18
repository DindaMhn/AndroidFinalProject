package com.example.androidfinalproject.screens.provider

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.home.Asset
import com.example.androidfinalproject.provider.home.ProviderHomeViewModel
import com.example.androidfinalproject.provider.profile.ProviderProfileViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_add_asset.*
import kotlinx.android.synthetic.main.fragment_profile_provider.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddAssetFragment : Fragment(), View.OnClickListener, OnMapReadyCallback {
    @Inject
    lateinit var providerHomeViewModel: ProviderHomeViewModel
    var sharedPreferences: SharedPreferences? = null
    val OPEN_CAMERA_REQUEST_CODE = 13
    val SELECT_FILE_FORM_STORAGE = 66
    val FINE_LOCATION_RQ = 101
    lateinit var currentPhotoPath: String
    lateinit var photoFile: File
    lateinit var gmap: GoogleMap
    var deviceLocation: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        add_asset_map_view.onCreate(savedInstanceState)
        add_asset_map_view.onResume()
        add_asset_map_view.getMapAsync(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_asset, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addAssetProsesButton.setOnClickListener(this)
        uploadAssetPhoto.setOnClickListener(this)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 66 && resultCode == Activity.RESULT_OK) {
            val getFile = ImagePicker.getFile(data)
            val imageBitmap = BitmapFactory.decodeFile(getFile?.absolutePath)

            val requestBody = getFile?.asRequestBody("multipart".toMediaTypeOrNull())
            val imageFileChoosed =
                MultipartBody.Part.createFormData("photo", getFile?.name, requestBody!!)

            val result = MultipartBody.Part.createFormData(
                "result",
                """{"provider_id": "${sharedPreferences?.getString("ID_PROVIDER", "").toString()}",
                        "asset_name":"${assetNameInput.text.toString()}",
                        "asset_area":${assetAreaInput.text.toString()},
                        "longitude":${longitudeInput.text},
                        "latitude":${latitudeInput.text},
                        "car_capacity":${carCapInput.text.toString()},
                        "motorcycle_capacity":${motorCapInput.text.toString()},
                        "bicycle_capacity":${bicycleCapInput.text.toString()}}"""
            )

            var token = sharedPreferences?.getString("TOKEN_PROVIDER", "").toString()
            providerHomeViewModel.createAsset(
                token, imageFileChoosed, result
            )
            imageUrlAsset.text = Editable.Factory.getInstance().newEditable(getFile?.absolutePath)
            imageAsset.setImageBitmap(imageBitmap)
        }

    }

    private fun imagePicker() {
        ImagePicker.with(this)
            .compress(1024)
            .maxResultSize(
                1080,
                1080
            )
            .start(66)
    }

    override fun onClick(v: View?) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()

        when (v) {
            addAssetProsesButton -> {
                if (assetNameInput.text.toString() == "" || assetAreaInput.text.toString() == "" ||
                    longitudeInput.text.toString() == "" || latitudeInput.text.toString() == "" ||
                    carCapInput.text.toString() == "" || motorCapInput.text.toString() == "" ||
                    bicycleCapInput.text.toString() == ""
                ) {
                    Toast.makeText(this.context, "Must be filled", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    alertDialog.setTitle("Add Asset")
                    alertDialog.setMessage("Add Asset Success")

                    alertDialog.setButton(
                        AlertDialog.BUTTON_POSITIVE, "OK"
                    ) { dialog, which -> activity?.onBackPressed() }
                    alertDialog.show()

                    val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)

                    val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
                    layoutParams.weight = 10f
                    btnPositive.layoutParams = layoutParams
                }
            }
            uploadAssetPhoto -> {
                imagePicker()
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            gmap = it
            getLastLocation()
            setMapLongClick(gmap)
        }
    }


    private fun checkPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                context as Activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                context as Activity,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
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
        if (requestCode == FINE_LOCATION_RQ) {
            if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug", "You Have The Permission")
            }
        }
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    var deviceLocation = task.result
                    if (deviceLocation == null) {
                        getNewLocation()
                    } else {
                        gmap.isMyLocationEnabled = true
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            // Got last known location. In some rare situations this can be null.
                            // 3
                            if (location != null) {
                                deviceLocation = location
                                val currentLatLng = LatLng(location.latitude, location.longitude)
                                gmap.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        currentLatLng,
                                        12f
                                    )
                                )

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
            } else {
                Toast.makeText(context, "Please Enable Your Location Service", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity as Activity, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), FINE_LOCATION_RQ
        )
    }

    private fun getNewLocation() {
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

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            var lastLocation = p0.lastLocation
            deviceLocation = lastLocation
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            map.clear()
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
            )
            var longitude = latLng.longitude.toString()
            var latitude = latLng.latitude.toString()
            longitudeInput.text = Editable.Factory.getInstance()
                .newEditable(longitude)
            latitudeInput.text = Editable.Factory.getInstance()
                .newEditable(latitude)
        }
    }

}