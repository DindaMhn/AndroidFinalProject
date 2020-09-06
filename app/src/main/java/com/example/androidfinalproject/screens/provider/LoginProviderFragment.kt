package com.example.androidfinalproject.screens.provider

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.provider.account.Provider
import com.example.androidfinalproject.provider.account.ProviderViewModel
import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.user.account.UserViewModel
import kotlinx.android.synthetic.main.fragment_login_provider.*
import javax.inject.Inject

class LoginProviderFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var providerViewModel: ProviderViewModel

    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_provider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        providerViewModel.providerResponse.observe(
            viewLifecycleOwner, Observer {
                if (it.status == 400.toString() && it.message == "Login Provider Failed") {
                    Toast.makeText(
                        this.context,
                        "Invalid Login",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (it.status == 200.toString()) {
                    Toast.makeText(this.context, "Login Success", Toast.LENGTH_SHORT)
                        .show()
                    val bundle = bundleOf(Pair("id_provider", it.result))
                    providerViewModel.providerData.observe(
                        viewLifecycleOwner, Observer {
                            if (it != null) {
                                with(sharedPreferences?.edit()) {
                                    this?.putString(
                                        getString(R.string.id_provider_key),
                                        it.id
                                    )
                                    this?.commit()
                                }
                            }
                            view?.findNavController()
                                ?.navigate(R.id.action_loginProviderFragment_to_menuProviderActivity)
                        })
                }
            })


        registerProviderText.setOnClickListener(this)
        loginProviderButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            registerProviderText -> {
                v?.findNavController()
                    ?.navigate(R.id.action_loginProviderFragment_to_chooseUserFragment)
            }
            loginProviderButton -> {
                val providerLogin =
                    Provider(
                        username = userNameInputProviderLogin.text.toString(),
                        email = userNameInputProviderLogin.text.toString(),
                        password = passwordInputProviderLogin.text.toString()
                    )
                if (userNameInputProviderLogin.text.toString() == "" ||
                    passwordInputProviderLogin.text.toString() == ""
                ) {
                    Toast.makeText(this.context, "Must be Field", Toast.LENGTH_SHORT).show()
                } else {
                    providerViewModel.loginProvider(providerLogin)
                }

            }
        }
    }
}