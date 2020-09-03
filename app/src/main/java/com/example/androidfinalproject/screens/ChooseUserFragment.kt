package com.example.androidfinalproject.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.androidfinalproject.R
import kotlinx.android.synthetic.main.fragment_choose_user.*

class ChooseUserFragment : Fragment(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseProviderButton.setOnClickListener(this)
        chooseUserButton.setOnClickListener(this)
        LoginText.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            chooseProviderButton -> {
                v?.findNavController()
                    ?.navigate(R.id.action_chooseUserFragment_to_providerRegisterFragment)
            }
            chooseUserButton->{
                v?.findNavController()
                    ?.navigate(R.id.action_chooseUserFragment_to_userRegisterFragment)
            }
            LoginText->{
                v?.findNavController()
                    ?.navigate(R.id.action_global_loginFragment)
            }
        }
    }
}