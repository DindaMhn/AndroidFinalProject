package com.example.androidfinalproject.screens.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_home_user.*
import javax.inject.Inject

class HomeUserFragment : Fragment(), View.OnClickListener {
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
        return inflater.inflate(R.layout.fragment_home_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topUpButton.setOnClickListener(this)
        println("MASUK")
        println("ID USER"+arguments?.getString("user_id").toString())
//        val id = sharedPreferences?.getString(
//            getString(R.string.id_key),
//            getString(R.string.default_value)
//        )
        userViewModel.getUserSaldo(arguments?.getString("user_id").toString())
//println("ID USER HOME"+id)
//        println("ID HOME USER WITH BUNDLE"+arguments?.getString("user_id").toString())
        userViewModel.userSaldoData.observe(viewLifecycleOwner, Observer {
            saldoUserText.text = "Rp. ${it.saldo}"
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            topUpButton -> {
                v?.findNavController()?.navigate(R.id.action_homeUserFragment_to_topUpSaldoFragment)
            }
        }
    }
}