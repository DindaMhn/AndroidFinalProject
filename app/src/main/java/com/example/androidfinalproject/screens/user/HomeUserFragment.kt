package com.example.androidfinalproject.screens.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.account.UserViewModel
import com.example.androidfinalproject.user.home.UserHomeRepository
import com.example.androidfinalproject.user.home.UserHomeViewModel
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_home_user.*
import javax.inject.Inject

class HomeUserFragment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var userHomeViewModel: UserHomeViewModel
    var sharedPreferences: SharedPreferences? = null
    var sampleImages = intArrayOf(
        R.drawable.park_1,
        R.drawable.park_2,
        R.drawable.parking_3,
        R.drawable.parking_4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )

    }
    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here
            imageView.setImageResource(sampleImages[position])
            imageView.scaleType = ImageView.ScaleType.FIT_XY
        }
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
        val id = sharedPreferences?.getString(
            "ID_USER", ""
        )

        val carouselView = view.findViewById(R.id.carouselView) as CarouselView;
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(sampleImages.size);
        userHomeViewModel.getUserSaldo(id.toString())
        userHomeViewModel.userSaldoData.observe(viewLifecycleOwner, Observer {
            saldoUserText.text = "Rp. ${it.saldo}"
        })
        userHomeViewModel.getUserTicket(id.toString())


        userHomeViewModel.userTicket.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                with(sharedPreferences?.edit()){
                    this?.putString("ID_TICKET",it.id)
                    this?.putString("STATUS_TICKET", it.status)
                    this?.putString("BOOK_AT_TICKET", it.book_at)
                    this?.putString("ID_ASSET_TICKET",it.asset_id)
                    this?.putString("ID_FEE_TICKET", it.fee_id)
                    this?.putString("ID_VEHICLE_TICKET", it.vehicle_id)
                    this?.commit()
                }
                ticketAssetName.text = it.asset_name
                typeVehicle.text = it.vehicle_type
                plat.text = it.license_plate
                book_at.text = it.book_at
            } else {
                ticketAssetName.text = "No Ticket"
                typeVehicle.text = ""
                plat.text = ""
                book_at.text = ""
            }
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