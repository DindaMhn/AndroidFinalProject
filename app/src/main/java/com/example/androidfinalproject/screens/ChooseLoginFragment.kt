package com.example.androidfinalproject.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.androidfinalproject.R
import kotlinx.android.synthetic.main.fragment_choose_login.*

class ChooseLoginFragment : Fragment(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseProviderLoginButton.setOnClickListener(this)
        chooseUserLoginButton.setOnClickListener(this)
        registerText.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            registerText->{
                v?.findNavController()?.navigate(R.id.action_chooseLoginFragment_to_chooseUserFragment)
            }
            chooseProviderLoginButton->{
                v?.findNavController()?.navigate(R.id.action_chooseLoginFragment_to_loginProviderFragment)
            }
            chooseUserLoginButton->{
                v?.findNavController()?.navigate(R.id.action_chooseLoginFragment_to_loginUserFragment)
            }
        }
    }
}