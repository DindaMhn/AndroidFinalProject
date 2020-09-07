package com.example.androidfinalproject.screens.provider

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.profile.ProviderProfileViewModel
import com.example.androidfinalproject.provider.profile.ProviderUpdate
import kotlinx.android.synthetic.main.fragment_profile_provider.*
import kotlinx.android.synthetic.main.fragment_profile_user_fagment.*
import java.util.*
import javax.inject.Inject

class ProfileProviderFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var providerProfileViewModel: ProviderProfileViewModel
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
//        providerProfileViewModel.providerData.observe(viewLifecycleOwner, Observer {
////            if (it != null) {
////                with(sharedPreferences?.edit()) {
////                    this?.putString(
////                        getString(R.string.fullname_provider_key),
////                        it.fullname
////                    )
////                    println("FULLNAME" + it.fullname)
////                    this?.putString(
////                        getString(R.string.borndate_provider_key),
////                        it.borndate
////                    )
////                    this?.putString(
////                        getString(R.string.phone_provider_key),
////                        it.phone_number
////                    )
////                    this?.putString(
////                        getString(R.string.address_provider_key),
////                        it.address
////                    )
////                    this?.commit()
////                }
////            }
////        })
////        val fullname = sharedPreferences?.getString(
////            getString(R.string.fullname_provider_key),
////            getString(R.string.default_value)
////        )
////        val address = sharedPreferences?.getString(
////            getString(R.string.address_provider_key),
////            getString(R.string.default_value)
////        )
////        val borndate = sharedPreferences?.getString(
////            getString(R.string.borndate_provider_key),
////            getString(R.string.default_value)
////        )
////        val phone = sharedPreferences?.getString(
////            getString(R.string.phone_provider_key),
////            getString(R.string.default_value)
////        )
        providerProfileViewModel.getById(sharedPreferences?.getString("ID_PROVIDER", "").toString())
        println("ID_PROVIDER" + sharedPreferences?.getString("ID_PROVIDER", "").toString())
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
//        fNameEditTextProvider.text =
//            Editable.Factory.getInstance().newEditable(fullname.toString())
//        phoneNumberEditTextProvider.text =
//            Editable.Factory.getInstance().newEditable(phone.toString())
//        addressEditTextProvider.text =
//            Editable.Factory.getInstance()
//                .newEditable(address.toString())
//        bornDateEditTextProvider.text =
//            Editable.Factory.getInstance().newEditable(borndate.toString())

        ChangePhotoProvider.setOnClickListener(this)
        simpanEditProviderButton.setOnClickListener(this)
        deleteProviderPhoto.setOnClickListener(this)
        logoutProviderButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()

        when (v) {
            simpanEditProviderButton -> {
                val id = sharedPreferences?.getString("ID_PROVIDER", "")
                println("ID PROFILE PROVIDER" + id.toString())
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
                activity?.finish()
            }
        }
    }
}