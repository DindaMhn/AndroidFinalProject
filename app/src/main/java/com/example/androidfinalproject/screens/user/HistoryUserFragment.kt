package com.example.androidfinalproject.screens.user

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
    @Inject lateinit var ticketViewModel: TicketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
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
        recycle_view_history_list.layoutManager=LinearLayoutManager(activity)
        var user_id = "177f3d50-eb57-11ea-86a5-b4a9fc958140"
        ticketViewModel.historyPayment(user_id)
        ticketViewModel.historyPaymentList.observe(viewLifecycleOwner, Observer {
            historyUserRecycleAdapter = HistoryUserRecycleAdapter(it, activity)
            recycle_view_history_list.adapter =historyUserRecycleAdapter
        })
    }

}