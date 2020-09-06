package com.example.androidfinalproject.screens.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.home.UserHomeViewModel
import com.example.androidfinalproject.user.home.UserWallet
import kotlinx.android.synthetic.main.fragment_add_asset.*
import kotlinx.android.synthetic.main.fragment_top_up_saldo.*
import javax.inject.Inject


class TopUpSaldoFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var userHomeViewModel: UserHomeViewModel
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
        return inflater.inflate(R.layout.fragment_top_up_saldo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prosesButton.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onClick(v: View?) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val id_wallet = sharedPreferences?.getString(
            getString(R.string.wallet_id_key),
            getString(R.string.default_value)
        )
        when (v) {

            prosesButton -> {
                userHomeViewModel.updateSaldoUser(
                    id_wallet.toString(),
                    UserWallet(debit = debitInput.text.toString())
                )
                println(arguments?.getString("wallet").toString())
                alertDialog.setTitle("TopUp Saldo")
                alertDialog.setMessage("TopUp Success")

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes"
                ) { dialog, which -> activity?.onBackPressed() }
                alertDialog.show()

                val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

                val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
                layoutParams.weight = 10f
                btnPositive.layoutParams = layoutParams
                btnNegative.layoutParams = layoutParams
            }
        }
    }
}