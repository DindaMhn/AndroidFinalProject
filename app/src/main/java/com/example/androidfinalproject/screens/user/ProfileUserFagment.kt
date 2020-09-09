package com.example.androidfinalproject.screens.user

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
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.activity.MainActivity
import com.example.androidfinalproject.user.profile.UserProfileViewModel
import com.example.androidfinalproject.user.profile.UserUpdate
import kotlinx.android.synthetic.main.fragment_profile_user_fagment.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProfileUserFagment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var userProfileViewModel: UserProfileViewModel
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
        return inflater.inflate(R.layout.fragment_profile_user_fagment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mPickTimeBtn = view?.findViewById<TextView>(R.id.calenderPickerUser)
        val textView = view?.findViewById<EditText>(R.id.bornDateEditTextUser)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        mPickTimeBtn?.setOnClickListener {
            val monthView = month + 1
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    // Display Selected date in TextView
                    textView?.setText("$year-$monthView-$day")
                }, year, month, day
            )
            dpd.show()
        }
        userProfileViewModel.getById(sharedPreferences?.getString("ID_USER", "").toString())
        userProfileViewModel.getUserPhoto(
            sharedPreferences?.getString("ID_USER", "").toString(),
            photoProfileUser,
            this.requireActivity()
        )
        userProfileViewModel.userData.observe(viewLifecycleOwner, Observer {
            fNameUserEditTextUser.text =
                Editable.Factory.getInstance().newEditable(it.fullname.toString())
            phoneNumberEditTextUser.text =
                Editable.Factory.getInstance().newEditable(it.phone_number.toString())
            addressEditTextUser.text =
                Editable.Factory.getInstance()
                    .newEditable(it.address.toString())
            bornDateEditTextUser.text =
                Editable.Factory.getInstance().newEditable(it.borndate.toString())
        })
        deleteUserPhoto.setOnClickListener(this)
        ChangePhotoUser.setOnClickListener(this)
        simpanEditUserButton.setOnClickListener(this)
        logoutUserButton.setOnClickListener(this)
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
            val userId = MultipartBody.Part.createFormData(
                "id",
                sharedPreferences?.getString("ID_USER", "").toString()
            )
            userProfileViewModel.updateUserPhoto(
                sharedPreferences?.getString("ID_USER", "").toString(),
                imageFileChoosed, userId
            )
            photoProfileUser.setImageBitmap(imageBitmap)
        }
        if (requestCode == SELECT_FILE_FORM_STORAGE && resultCode == Activity.RESULT_OK) {
            val originalPath = getOriginalPathFromUri(data?.data!!)
            val imageFile: File = File(originalPath)
            val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)

            val requestBody = imageFile.asRequestBody("multipart".toMediaTypeOrNull())
            val imageFileChoosed =
                MultipartBody.Part.createFormData("photo", imageFile.name, requestBody)
            val userId = MultipartBody.Part.createFormData(
                "id",
                sharedPreferences?.getString("ID_USER", "").toString()
            )
            userProfileViewModel.updateUserPhoto(
                sharedPreferences?.getString("ID_USER", "").toString(),
                imageFileChoosed, userId
            )
            photoProfileUser.setImageBitmap(imageBitmap)
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
            logoutUserButton -> {
                with(sharedPreferences?.edit()) {
                    this?.putBoolean(
                        "ISLOGGEDIN_USER",
                        false
                    )
                    this?.clear()
                    this?.commit()
                }
                startActivity(Intent(this.context, MainActivity::class.java))
            }
            simpanEditUserButton -> {
                val id = sharedPreferences?.getString("ID_USER", "")
                userProfileViewModel.updateUserProfile(
                    id.toString(),
                    UserUpdate(
                        borndate = bornDateEditTextUser.text.toString(),
                        address = addressEditTextUser.text.toString()
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
            ChangePhotoUser -> {
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
            deleteUserPhoto -> {
                userProfileViewModel.deleteUserPhoto(arguments?.getString("id").toString())
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
        }
    }
}