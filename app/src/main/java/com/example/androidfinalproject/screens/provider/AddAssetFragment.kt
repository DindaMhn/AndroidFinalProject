package com.example.androidfinalproject.screens.provider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.home.Asset
import com.example.androidfinalproject.provider.home.ProviderHomeViewModel
import kotlinx.android.synthetic.main.fragment_add_asset.*
import javax.inject.Inject

class AddAssetFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var providerHomeViewModel: ProviderHomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)

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
    }

    override fun onClick(v: View?) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        when (v) {
            addAssetProsesButton -> {
                providerHomeViewModel.createAsset(
                    Asset(
                        provider_id = arguments?.getString("id").toString(),
                        asset_name = assetNameInput.text.toString(),
                        asset_area = assetAreaInput.text.toString(),
                        longitude = longitudeInput.text.toString(),
                        latitude = latitudeInput.text.toString(),
                        car_capacity = carCapInput.text.toString(),
                        motorcycle_capacity = motorCapInput.text.toString(),
                        bicycle_capacity = bicycleCapInput.text.toString(),
                        photo = imageUrlAsset.text.toString()
                    )
                )
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
    }
}