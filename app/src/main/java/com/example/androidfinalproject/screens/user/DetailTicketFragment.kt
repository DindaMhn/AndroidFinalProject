package com.example.androidfinalproject.screens.user

import android.content.Context
import android.content.SharedPreferences
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
import com.example.androidfinalproject.user.rating.RatingViewModel
import com.example.androidfinalproject.user.ticket.Ticket
import com.example.androidfinalproject.user.ticket.TicketViewModel
import kotlinx.android.synthetic.main.fragment_detail_ticket.*
import kotlinx.android.synthetic.main.fragment_scan_asset_qr_code.*
import kotlinx.android.synthetic.main.fragment_scan_asset_qr_code.detail_button_pay_ticket
import javax.inject.Inject


class DetailTicketFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var ticketViewModel: TicketViewModel

    @Inject
    lateinit var ratingViewModel: RatingViewModel
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
        return inflater.inflate(R.layout.fragment_detail_ticket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detail_button_pay_ticket.setOnClickListener(this)
        var id = sharedPreferences?.getString("ID_TICKET", "default").toString()
        var token = sharedPreferences?.getString("TOKEN", "").toString()

        println("ini_id ticket ${id}")
        ticketViewModel.getTicketViewByID(id,token)
        ticketViewModel.ticketView.observe(viewLifecycleOwner, Observer {
            detail_location_view.text = it.asset_name
            detail_time_start_parking_view.text = it.start_at
            detail_time_end_parking_view.text = it.finished_at
            detail_license_plate_view.text = it.license_plate
            detail_based_fee_view.text = "Rp. ${it.based_fee}"
            detail_fee_pay.text = "Rp ${it.pay_fee}"

        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.detail_button_pay_ticket -> {
                val ticketNew = Ticket(
                    id = sharedPreferences?.getString("ID_TICKET", "default").toString()
                    ,
                    user_id = sharedPreferences?.getString("ID_USER", "default").toString()
                    ,
                    asset_id = sharedPreferences?.getString("ID_ASSET_TICKET", "default").toString()
                    ,
                    fee_id = sharedPreferences?.getString("ID_FEE_TICKET", "default").toString()
                    ,
                    vehicle_id = sharedPreferences?.getString("ID_VEHICLE_TICKET", "default")
                        .toString()
                    ,
                    license_plate = detail_license_plate_view.text.toString()
                    ,
                    book_at = sharedPreferences?.getString("BOOK_AT_TICKET", "default").toString()
                    ,
                    start_at = detail_time_start_parking_view.text.toString()
                    ,
                    finished_at = detail_time_end_parking_view.text.toString()
                    ,
                    status = sharedPreferences?.getString("STATUS_TICKET", "").toString()
                )
                println("vehicle")
                println(ticketNew.vehicle_id)
                println("fee")
                println(ticketNew.fee_id)
                var token = sharedPreferences?.getString("TOKEN", "").toString()
                ticketViewModel.paymentTicket(token,ticketNew)
                ticketViewModel.ticketResponse.observe(
                    viewLifecycleOwner, Observer {
                        if (it.status == 400.toString() && it.message == "Error") {
                            Toast.makeText(
                                this.context,
                                "Invalid Payment",
                                Toast.LENGTH_SHORT
                            ).show()
//                            mScannerView.resumeCameraPreview(this)
//                            initDefaultView()
                        } else if (it.status == 202.toString()) {
                            Toast.makeText(this.context, "Payment Success", Toast.LENGTH_SHORT)
                                .show()
                            var user_id =
                                sharedPreferences?.getString("ID_USER", "default").toString()
                            var asset_id =
                                sharedPreferences?.getString("ID_ASSET_TICKET", "default")
                                    .toString()
                            var token = sharedPreferences?.getString("TOKEN", "default").toString()
                            ratingViewModel.getStatusRating(user_id, asset_id, token)
                            ratingViewModel.statusRatingResponse.observe(
                                viewLifecycleOwner,
                                Observer { ratingResponse ->
                                    if (ratingResponse.result == "hello") {
                                        view?.findNavController()
                                            ?.navigate(R.id.action_detailTicketFragment_to_ratingAssetFragment)
                                    } else {
                                        view?.findNavController()
                                            ?.navigate(R.id.action_global_homeUserFragment)
                                    }
                                })
                        }
                    })
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }


}