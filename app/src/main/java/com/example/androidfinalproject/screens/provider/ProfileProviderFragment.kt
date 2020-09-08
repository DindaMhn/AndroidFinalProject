package com.example.androidfinalproject.screens.provider

import android.app.Activity
import android.app.DatePickerDialog
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
import android.widget.EditText
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.activity.MainActivity
import com.example.androidfinalproject.provider.profile.ProviderProfileViewModel
import com.example.androidfinalproject.provider.profile.ProviderUpdate
import kotlinx.android.synthetic.main.fragment_profile_provider.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProfileProviderFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var providerProfileViewModel: ProviderProfileViewModel
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
        return inflater.inflate(R.layout.fragment_profile_provider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mPickTimeBtn = view?.findViewById<TextView>(R.id.calenderPickerProvider)
        val textView = view?.findViewById<EditText>(R.id.bornDateEditTextProvider)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        mPickTimeBtn?.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    val monthView = month + 1
                    // Display Selected date in TextView
                    textView?.setText("$year-$monthView-$day")
                }, year, month, day
            )
            dpd.show()
        }

        providerProfileViewModel.getById(sharedPreferences?.getString("ID_PROVIDER", "").toString())
        providerProfileViewModel.getProviderPhoto(
            sharedPreferences?.getString("ID_PROVIDER", "").toString(),
            photoProfileProvider,
            this.requireActivity()
        )
        providerProfileViewModel.providerData.observe(viewLifecycleOwner, Observer {
            fNameEditTextProvider.text =
                Editable.Factory.getInstance().newEditable(it.fullname.toString())
            phoneNumberEditTextProvider.text =
                Editable.Factory.getInstance().newEditable(it.phone_number.toString())
            addressEditTextProvider.text =
                Editable.Factory.getInstance()
                    .newEditable(it.address.toString())
            bornDateEditTextProvider.text =
                Editable.Factory.getInstance().newEditable(it.borndate.toString())
        })
        ChangePhotoProvider.setOnClickListener(this)
        simpanEditProviderButton.setOnClickListener(this)
        deleteProviderPhoto.setOnClickListener(this)
        logoutProviderButton.setOnClickListener(this)
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
            val providerId = MultipartBody.Part.createFormData(
                "id",
                sharedPreferences?.getString("ID_PROVIDER", "").toString()
            )
            providerProfileViewModel.updateProviderPhoto(
                sharedPreferences?.getString("ID_PROVIDER", "").toString(),
                imageFileChoosed, providerId
            )
            photoProfileProvider.setImageBitmap(imageBitmap)
        }
        if (requestCode == SELECT_FILE_FORM_STORAGE && resultCode == Activity.RESULT_OK) {
            val originalPath = getOriginalPathFromUri(data?.data!!)
            val imageFile: File = File(originalPath)
            val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)

            val requestBody = imageFile.asRequestBody("multipart".toMediaTypeOrNull())
            val imageFileChoosed =
                MultipartBody.Part.createFormData("photo", imageFile.name, requestBody)
            val providerId = MultipartBody.Part.createFormData(
                "id",
                sharedPreferences?.getString("ID_PROVIDER", "").toString()
            )
            providerProfileViewModel.updateProviderPhoto(
                sharedPreferences?.getString("ID_PROVIDER", "").toString(),
                imageFileChoosed, providerId
            )
            photoProfileProvider.setImageBitmap(imageBitmap)
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
            simpanEditProviderButton -> {
                val id = sharedPreferences?.getString("ID_PROVIDER", "")
                if (bornDateEditTextProvider.text.toString() == "" || addressEditTextProvider.text.toString() == "") {
                    Toast.makeText(this.context, "Must be Field", Toast.LENGTH_SHORT).show()
                } else {
                    providerProfileViewModel.updateProviderProfile(
                        id.toString(),
                        ProviderUpdate(
                            borndate = bornDateEditTextProvider.text.toString(),
                            address = addressEditTextProvider.text.toString()
                        )
                    )
                    alertDialog.setTitle("Edit Profile")
                    alertDialog.setMessage("Edit Success")

                    alertDialog.setButton(
                        AlertDialog.BUTTON_POSITIVE, "OK"
                    ) { dialog, which -> dialog.dismiss() }
                    alertDialog.show()

                    val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)

                    val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
                    layoutParams.weight = 10f
                    btnPositive.layoutParams = layoutParams
                }
            }
            ChangePhotoProvider -> {
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
            deleteProviderPhoto -> {
                val id = sharedPreferences?.getString(
                    getString(R.string.id_provider_key),
                    getString(R.string.default_value)
                )
                providerProfileViewModel.deleteProviderPhoto(id.toString())
                alertDialog.setTitle("Delete Photo")
                alertDialog.setMessage("Delete Success")

                alertDialog.setButton(
                    AlertDialog.BUTTON_POSITIVE, "OK"
                ) { dialog, which -> dialog.dismiss() }
                alertDialog.show()

                val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)

                val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
                layoutParams.weight = 10f
                btnPositive.layoutParams = layoutParams
            }
            logoutProviderButton -> {
                with(sharedPreferences?.edit()) {
                    this?.putBoolean(
                        "ISLOGGEDIN_PROVIDER",
                        false
                    )
                    this?.clear()
                    this?.commit()
                }
                startActivity(Intent(this.context, MainActivity::class.java))
            }
        }
    }
}