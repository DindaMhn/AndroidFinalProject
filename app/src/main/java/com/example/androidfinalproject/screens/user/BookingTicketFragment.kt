package com.example.androidfinalproject.screens.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.home.UserHomeViewModel
import com.example.androidfinalproject.user.ticket.Ticket
import com.example.androidfinalproject.user.ticket.TicketViewModel
import kotlinx.android.synthetic.main.fragment_booking_ticket.*
import kotlinx.android.synthetic.main.fragment_profile_user_fagment.*
import javax.inject.Inject

class BookingTicketFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var ticketViewModel: TicketViewModel

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
        return inflater.inflate(R.layout.fragment_booking_ticket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()

        when (v) {
            bookButton -> {
                userHomeViewModel.getUserTicket(
                    sharedPreferences?.getString("ID_USER", "default").toString(),
                    sharedPreferences?.getString("TOKEN", "").toString()
                )
                userHomeViewModel.userResponse.observe(viewLifecycleOwner, Observer {
                    if (it.status != 400.toString()) {
                        Toast.makeText(this.context, "You are Already Booked", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        if (platNo.text.toString() == "") {
                            Toast.makeText(this.context, "Must be filled", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            if (vehicle_type.selectedItem.toString() == "CAR") {
                                ticketViewModel.createTicket(
                                    sharedPreferences?.getString("TOKEN", "").toString(),
                                    Ticket(
                                        user_id = sharedPreferences?.getString(
                                            "ID_USER",
                                            ""
                                        ).toString(),
                                        license_plate = platNo.text.toString(),
                                        asset_id = arguments?.getString("asset_id").toString(),
                                        vehicle_id = "1",
                                        fee_id = "1"
                                    )
                                )
                            } else if (vehicle_type.selectedItem.toString() == "MOTORCYCLE") {
                                ticketViewModel.createTicket(
                                    sharedPreferences?.getString("TOKEN", "").toString(),
                                    Ticket(
                                        user_id = sharedPreferences?.getString(
                                            "ID_USER",
                                            ""
                                        ).toString(),
                                        license_plate = platNo.text.toString(),
                                        asset_id = arguments?.getString("asset_id").toString(),
                                        vehicle_id = "2",
                                        fee_id = "2"
                                    )
                                )
                            } else {
                                ticketViewModel.createTicket(
                                    sharedPreferences?.getString("TOKEN", "").toString(),
                                    Ticket(
                                        user_id = sharedPreferences?.getString(
                                            "ID_USER",
                                            ""
                                        ).toString(),
                                        license_plate = platNo.text.toString(),
                                        asset_id = arguments?.getString("asset_id").toString(),
                                        vehicle_id = "3",
                                        fee_id = "3"
                                    )
                                )
                            }
                            alertDialog.setTitle("Booking Ticket")
                            alertDialog.setMessage("Booking Ticket Success")

                            alertDialog.setButton(
                                AlertDialog.BUTTON_POSITIVE, "OK"
                            ) { dialog, which ->
                                v?.findNavController()
                                    ?.navigate(R.id.homeUserFragment)
                            }
                            alertDialog.show()

                            val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)

                            val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
                            layoutParams.weight = 10f
                            btnPositive.layoutParams = layoutParams
                        }
                    }
                })
            }
        }
    }
}