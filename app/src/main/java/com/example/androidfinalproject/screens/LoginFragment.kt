package com.example.androidfinalproject.screens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.activity.user.MenuUserActivity
import com.example.androidfinalproject.provider.Provider
import com.example.androidfinalproject.provider.ProviderViewModel
import com.example.androidfinalproject.user.User
import com.example.androidfinalproject.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var providerViewModel: ProviderViewModel
    @Inject
    lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        providerViewModel.providerResponse.observe(
            viewLifecycleOwner, Observer {
                if (it.status == 400.toString() && it.message=="Login Provider Failed") {
                    Toast.makeText(
                        this.context,
                        "Invalid Login",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (it.status == 200.toString()){
                    Toast.makeText(this.context, "Login Success", Toast.LENGTH_SHORT)
                        .show()
                    view?.findNavController().navigate(R.id.action_loginFragment_to_menuProviderActivity)
                }
            })
        userViewModel.userResponse.observe(
            viewLifecycleOwner, Observer {
                if (it.status == 400.toString() && it.message=="Login User Failed") {
                    Toast.makeText(
                        this.context,
                        "Invalid Login",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (it.status == 200.toString()){
                    Toast.makeText(this.context, "Login Success", Toast.LENGTH_SHORT)
                        .show()
//                    view?.findNavController().navigate(R.id.action_loginFragment_to_menuUserActivity)
                }
            })

        registerText.setOnClickListener(this)
        loginButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            registerText -> {
                v?.findNavController()?.navigate(R.id.action_loginFragment_to_chooseUserFragment)
            }
            loginButton -> {
                val providerLogin = Provider(
                    username = userNameInputLogin.text.toString(),
                    email = userNameInputLogin.text.toString(),
                    password = passwordInputLogin.text.toString()
                )
                if (userNameInputLogin.text.toString() == "" ||
                    passwordInputLogin.text.toString() == ""
                ) {
                    Toast.makeText(this.context, "Must be Field", Toast.LENGTH_SHORT).show()
                } else {
                    providerViewModel.loginProvider(providerLogin)
                }
                val userLogin = User(
                    username = userNameInputLogin.text.toString(),
                    password = passwordInputLogin.text.toString()
                )
                if (userNameInputLogin.text.toString() == "" ||
                    passwordInputLogin.text.toString() == ""
                ) {
                    Toast.makeText(this.context, "Must be Field", Toast.LENGTH_SHORT).show()
                } else {
                    userViewModel.loginUser(userLogin)
//                    userViewModel.userData.observe(
//                        viewLifecycleOwner, Observer {
//                            val bundle = bundleOf(Pair("id_user",it.id))
//                            println("ID USER LOGIN"+it.id)
//                            val intent = Intent(activity, MenuUserActivity::class.java)
//                            intent?.putExtra("id_user",it.id)
//                            activity?.startActivity(intent)
//                            println("INTENT"+intent)
                            view?.findNavController()?.navigate(R.id.action_loginFragment_to_menuUserActivity)
                        }

                }
            }
        }
    }