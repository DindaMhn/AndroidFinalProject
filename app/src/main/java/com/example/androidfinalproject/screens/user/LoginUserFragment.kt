package com.example.androidfinalproject.screens.user

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
import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.user.account.UserViewModel
import kotlinx.android.synthetic.main.fragment_login_user.*
import javax.inject.Inject


class LoginUserFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var userViewModel: UserViewModel
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
        return inflater.inflate(R.layout.fragment_login_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.userResponse.observe(
            viewLifecycleOwner, Observer {
                if (it.status == 400.toString() && it.message == "Login User Failed") {
                    Toast.makeText(
                        this.context,
                        "Invalid Login",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (it.status == 200.toString()) {
                    Toast.makeText(this.context, "Login Success", Toast.LENGTH_SHORT)
                        .show()
                    userViewModel.userData.observe(
                        viewLifecycleOwner, Observer {
                            println("ID USER LOGIN" + it.id)
                            if (it != null) {
                                with(sharedPreferences?.edit()) {
                                    this?.putString(
                                        getString(R.string.id_key),
                                        it.id
                                    )
                                    this?.putString(
                                        getString(R.string.wallet_id_key),
                                        it.id_wallet
                                    )
                                    this?.commit()
                                }
                            }
                            view?.findNavController()
                                ?.navigate(R.id.action_loginUserFragment_to_menuUserActivity)
                        })
                }
            })
        registerUserText.setOnClickListener(this)
        loginUserButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            registerUserText -> {
                view?.findNavController()
                    ?.navigate(R.id.action_loginUserFragment_to_chooseUserFragment)
            }
            loginUserButton -> {
                val userLogin =
                    User(
                        email = userNameInputUserLogin.text.toString(),
                        username = userNameInputUserLogin.text.toString(),
                        password = passwordInputUserLogin.text.toString()
                    )
                if (userNameInputUserLogin.text.toString() == "" ||
                    passwordInputUserLogin.text.toString() == ""
                ) {
                    Toast.makeText(this.context, "Must be Field", Toast.LENGTH_SHORT).show()
                } else {
                    userViewModel.loginUser(userLogin)
                }
            }
        }
    }
}