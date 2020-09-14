package com.example.androidfinalproject.screens.provider

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.home.Asset
import com.example.androidfinalproject.provider.home.ProviderHomeViewModel
import com.example.androidfinalproject.provider.profile.ProviderProfileViewModel
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

class AddAssetFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var providerHomeViewModel: ProviderHomeViewModel
    var sharedPreferences: SharedPreferences? = null
    val OPEN_CAMERA_REQUEST_CODE = 13
    val SELECT_FILE_FORM_STORAGE = 66
    lateinit var currentPhotoPath: String
    lateinit var photoFile: File

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
        return inflater.inflate(R.layout.fragment_add_asset, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addAssetProsesButton.setOnClickListener(this)
        uploadAssetPhoto.setOnClickListener(this)
    }

    fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.resolveActivity(this?.requireActivity().packageManager)
        photoFile = createImageFile()
        val photoURI =
            FileProvider.getUriForFile(
                requireContext(),
                "com.example.androidfinalproject.fileProvider",
                photoFile
            )
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(cameraIntent, OPEN_CAMERA_REQUEST_CODE)
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

    fun browseFile() {
        val selectFileIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(selectFileIntent, SELECT_FILE_FORM_STORAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = BitmapFactory.decodeFile(photoFile.absolutePath)

            val requestBody = photoFile.asRequestBody("multipart".toMediaTypeOrNull())
            val imageFileChoosed =
                MultipartBody.Part.createFormData("photo", photoFile.name, requestBody)

            val result = MultipartBody.Part.createFormData(
                "result",
                """{"provider_id": "${sharedPreferences?.getString("ID_PROVIDER", "").toString()}",
                        "asset_name":"${assetNameInput.text.toString()}",
                        "asset_area":${assetAreaInput.text.toString()},
                        "longitude":${longitudeInput.text.toString()},
                        "latitude":${latitudeInput.text.toString()},
                        "car_capacity":${carCapInput.text.toString()},
                        "motorcycle_capacity":${motorCapInput.text.toString()},
                        "bicycle_capacity":${bicycleCapInput.text.toString()}}"""
            )
            var token = sharedPreferences?.getString("TOKEN", "").toString()
            providerHomeViewModel.createAsset(
                token, imageFileChoosed, result
            )
            imageUrlAsset.text = Editable.Factory.getInstance().newEditable(photoFile.absolutePath)
            imageAsset.setImageBitmap(imageBitmap)
        }
        if (requestCode == SELECT_FILE_FORM_STORAGE && resultCode == Activity.RESULT_OK) {
            val originalPath = getOriginalPathFromUri(data?.data!!)
            val imageFile: File = File(originalPath)
            val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)

            val requestBody = imageFile.asRequestBody("multipart".toMediaTypeOrNull())
            val imageFileChoosed =
                MultipartBody.Part.createFormData("photo", imageFile.name, requestBody)

            val result = MultipartBody.Part.createFormData(
                "result",
                """{"provider_id": "${sharedPreferences?.getString("ID_PROVIDER", "").toString()}",
                        "asset_name":"${assetNameInput.text.toString()}",
                        "asset_area":${assetAreaInput.text.toString()},
                        "longitude":${longitudeInput.text.toString()},
                        "latitude":${latitudeInput.text.toString()},
                        "car_capacity":${carCapInput.text.toString()},
                        "motorcycle_capacity":${motorCapInput.text.toString()},
                        "bicycle_capacity":${bicycleCapInput.text.toString()}}"""
            )
            var token = sharedPreferences?.getString("TOKEN_PROVIDER", "").toString()
            providerHomeViewModel.createAsset(
                token, imageFileChoosed, result
            )
            imageUrlAsset.text = Editable.Factory.getInstance().newEditable(imageFile.absolutePath)

            imageAsset.setImageBitmap(imageBitmap)

        }
    }

    fun getOriginalPathFromUri(contentUri: Uri): String? {
        var originalPath: String? = null
        val projection =
            arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            activity?.contentResolver?.query(contentUri, projection, null, null, null)
        if (cursor?.moveToFirst()!!) {
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            originalPath = cursor.getString(columnIndex)
        }
        return originalPath
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
                val changeImageDialog = AlertDialog.Builder(requireContext())
                changeImageDialog.setTitle(R.string.change_photo_prompt).setItems(
                    R.array.change_photo_arrays,
                    DialogInterface.OnClickListener { dialog, selectedOption ->
                        if (selectedOption == 0) {
                            openCamera()
                        } else if (selectedOption == 1) {
                            browseFile()
                        }
                    }).show()
            }
        }
    }
}