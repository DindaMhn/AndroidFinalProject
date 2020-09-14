package com.example.androidfinalproject.screens.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.recycleview.user.HistoryUserRecycleAdapter
import com.example.androidfinalproject.user.ticket.TicketViewModel
import kotlinx.android.synthetic.main.fragment_history_user.*
import javax.inject.Inject


class HistoryUserFragment : Fragment() {
    private lateinit var historyUserRecycleAdapter: HistoryUserRecycleAdapter

    @Inject
    lateinit var ticketViewModel: TicketViewModel
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
        return inflater.inflate(R.layout.fragment_history_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewHistoryTicket()
    }

    override fun onResume() {
        super.onResume()
        viewHistoryTicket()

    }

    private fun viewHistoryTicket() {
        recycle_view_history_list.layoutManager = LinearLayoutManager(activity)
        var user_id = sharedPreferences?.getString("ID_USER", "").toString()
        var token = sharedPreferences?.getString("TOKEN", "").toString()
        ticketViewModel.historyPayment(user_id, token)
        ticketViewModel.historyPaymentList.observe(viewLifecycleOwner, Observer {
            historyUserRecycleAdapter = HistoryUserRecycleAdapter(it, activity,token)

            recycle_view_history_list.adapter = historyUserRecycleAdapter
        })
    }

}