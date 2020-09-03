package com.example.androidfinalproject.screens.provider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.androidfinalproject.R
import kotlinx.android.synthetic.main.fragment_provider_register.*

class ProviderRegisterFragment : Fragment(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_provider_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoginProviderText.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            LoginProviderText -> {
                v?.findNavController()
                    ?.navigate(R.id.action_global_loginFragment)
            }
        }
    }
}