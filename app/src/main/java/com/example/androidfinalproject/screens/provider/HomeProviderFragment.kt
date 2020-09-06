package com.example.androidfinalproject.screens.provider

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.home.ProviderHomeViewModel
import kotlinx.android.synthetic.main.fragment_add_asset.*
import kotlinx.android.synthetic.main.fragment_home_provider.*
import javax.inject.Inject

class HomeProviderFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var providerHomeViewModel: ProviderHomeViewModel
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
        return inflater.inflate(R.layout.fragment_home_provider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addAssetButton.setOnClickListener(this)
        val id = sharedPreferences?.getString(
            getString(R.string.id_provider_key),
            getString(R.string.default_value)
        )
        providerHomeViewModel.getSaldoProvider(id.toString())
        providerHomeViewModel.providerResponse.observe(viewLifecycleOwner, Observer {
            saldoProviderText.text = "Rp. ${it.result}"
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            addAssetButton -> {
                v?.findNavController()
                    ?.navigate(R.id.action_homeProviderFragment_to_addAssetFragment)
            }
        }
    }
}