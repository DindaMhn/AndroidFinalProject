package com.example.androidfinalproject.screens.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.user.account.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_register.*
import javax.inject.Inject

class UserRegisterFragment : Fragment(), View.OnClickListener {
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
        return inflater.inflate(R.layout.fragment_user_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.userResponse.observe(viewLifecycleOwner,
            Observer {
                if(it.status==400.toString()){
                    Toast.makeText(this.context, "Username/Email Exist", Toast.LENGTH_SHORT)
                        .show()
                }else if (it.status == 200.toString()) {
                    Toast.makeText(this.context, "Register Success", Toast.LENGTH_SHORT)
                        .show()
                    view?.findNavController()
                        ?.navigate(R.id.action_global_loginUserFragment)
                }
            })

        LoginUserText.setOnClickListener(this)
        regisButtonUser.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            LoginUserText -> {
                v?.findNavController()
                    ?.navigate(R.id.action_global_chooseLoginFragment)
            }
            regisButtonUser->{
                val userData = User(
                    email = inputEmailRegisUser.text.toString(),
                    username = inputUnameRegisUser.text.toString(),
                    password = InputPwRegisUser.text.toString(),
                    phone_number = inputPhoneRegisUser.text.toString(),
                    fullname = inputFNameRegisUser.text.toString()
                )
                userViewModel.registerUser(userData)
            }
        }
    }
}