package com.example.androidfinalproject.screens.user

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.ticket.TicketViewModel
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_scan_asset_qr_code.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import javax.inject.Inject


class ScanAssetQrCodeFragment : Fragment(), ZXingScannerView.ResultHandler, View.OnClickListener {
    @Inject
    lateinit var ticketViewModel: TicketViewModel

    private lateinit var mScannerView: ZXingScannerView
    private var isCaptured = false
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScannerView()
        initDefaultView()
        detail_button_pay_ticket.setOnClickListener(this)

    }

    private fun initScannerView() {
        mScannerView = ZXingScannerView(context)
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        frame_layout_camera.addView(mScannerView)
    }

    private fun initDefaultView() {
        text_view_qr_code_value.text = "QR Code Value"
        detail_button_pay_ticket.visibility = View.GONE
    }

    override fun onStart() {
        doRequestPermission()
        mScannerView.startCamera()
        super.onStart()
    }

    private fun doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context?.let {
                    ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.CAMERA
                    )
                } != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                initScannerView()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_asset_qr_code, container, false)

    }

    override fun handleResult(rawResult: Result?) {
//        text_view_qr_code_value.text = rawResult?.text
        println("ini_raw_result: ${rawResult.toString()}")
        var idticket = sharedPreferences?.getString("ID_ASSET_TICKET", "").toString()
        if (rawResult.toString() != idticket) {
            Toast.makeText(
                this.context,
                "Youre In wrong Location",
                Toast.LENGTH_SHORT
            ).show()
            mScannerView.resumeCameraPreview(this)
            initDefaultView()
        } else {

            var id = sharedPreferences?.getString("ID_TICKET", "").toString()
            var status = sharedPreferences?.getString("STATUS_TICKET", "").toString()
            var token = sharedPreferences?.getString("TOKEN", "").toString()

            if (status == "B") {
                ticketViewModel.updateTicketStatusActive(id, token)
                Toast.makeText(
                    this.context,
                    "Ticket Active",
                    Toast.LENGTH_SHORT
                ).show()
                with(sharedPreferences?.edit()) {
                    this?.putString("STATUS_TICKET", "A")
                    this?.commit()
                }
                view?.findNavController()
                    ?.navigate(R.id.homeUserFragment)
            } else if (status == "A") {
                ticketViewModel.updateTicketStatus(id, token)
                Toast.makeText(
                    this.context,
                    "Ticket Valid",
                    Toast.LENGTH_SHORT
                ).show()
                view?.findNavController()
                    ?.navigate(R.id.detailTicketFragment)
            } else {
                Toast.makeText(
                    this.context,
                    "Ticket Invalid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

    override fun onClick(view: View?) {
//
//        when (view?.id) {
//
//            R.id.detail_button_pay_ticket -> {
//                val ticketNew = Ticket(
//                    id = "556169b9-eccc-11ea-83bf-b4a9fc958140"
//                    , user_id = "177f3d50-eb57-11ea-86a5-b4a9fc958140"
//                    , asset_id = text_view_qr_code_value.text.toString()
//                    , fee_id = "e3916ad8-eb5a-11ea-86a5-b4a9fc958140"
//                    , vehicle_id = "209f6e05-eb5a-11ea-86a5-b4a9fc958140"
//                    , license_plate = "B 3030 PTK"
//                    , book_at = "2020-09-02 10:28:13"
//                    , start_at = "2020-09-02 10:28:13"
//                    , finished_at = ""
//                    , status = "A"
//                )
//                ticketViewModel.paymentTicket(ticketNew)
//                ticketViewModel.ticketResponse.observe(
//                    viewLifecycleOwner, Observer {
//                        if (it.status == 400.toString() && it.message == "Error") {
//                            Toast.makeText(
//                                this.context,
//                                "Invalid Payment",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            mScannerView.resumeCameraPreview(this)
//                            initDefaultView()
//                        } else if (it.status == 202.toString()) {
//                            Toast.makeText(this.context, "Payment Success", Toast.LENGTH_SHORT)
//                                .show()
//                            view?.findNavController()
//                                ?.navigate(R.id.action_global_homeUserFragment)
//                        }
//                    })
//            }
//            else -> {
//                /* nothing to do in here */
//            }
//        }
    }


}