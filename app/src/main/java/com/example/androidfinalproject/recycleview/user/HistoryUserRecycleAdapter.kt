package com.example.androidfinalproject.recycleview.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.ticket.TicketView
import com.example.androidfinalproject.user.ticket.TicketViewModel
import kotlinx.android.synthetic.main.item_history_layout.view.*
import javax.inject.Inject

class HistoryUserRecycleAdapter(
    val paymentList: List<TicketView>,
    val getActivity: FragmentActivity?,
    val token: String
) : RecyclerView.Adapter<PaymentViewHolder>() {
    @Inject
    lateinit var ticketViewModel: TicketViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_layout, parent, false)
        (getActivity?.applicationContext as MyApplication).applicationComponent.inject(this)
        return PaymentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return paymentList.size
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {


        holder.location.text = paymentList[position].asset_name
        holder.startParking.text = paymentList[position].start_at
        holder.endParking.text = paymentList[position].finished_at
        holder.licensePlate.text = paymentList[position].license_plate
        holder.basedFee.text = paymentList[position].based_fee
        holder.feePay.text = paymentList[position].pay_fee
        holder.delete.setOnClickListener {
            ticketViewModel.deleteTicket(paymentList[position].id,token)
            it?.findNavController()
                ?.navigate(R.id.historyUserFragment)
//            ticketViewModel.historyPayment(sharedPreferences?.getString(
//                "ID_USER",
//                "default"
//            ))
        }
    }

}


class PaymentViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val location = v.findViewById<TextView>(R.id.item_location_view)
    val startParking = v.findViewById<TextView>(R.id.item_time_start_parking_view)
    val endParking = v.findViewById<TextView>(R.id.item_time_end_parking_view)
    val licensePlate = v.findViewById<TextView>(R.id.item_license_plate_view)
    val basedFee = v.findViewById<TextView>(R.id.item_based_fee_view)
    val feePay = v.findViewById<TextView>(R.id.item_fee_pay)
    val delete = v.findViewById<ImageView>(R.id.item_delete_ticket)
}


