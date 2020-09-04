package com.example.androidfinalproject.screens.user

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.androidfinalproject.R
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_scan_asset_qr_code.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ScanAssetQrCodeFragment : Fragment(), ZXingScannerView.ResultHandler, View.OnClickListener {

    private lateinit var mScannerView: ZXingScannerView
    private var isCaptured = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScannerView()
        initDefaultView()
        button_pay_ticket.setOnClickListener(this)
    }

    private fun initScannerView() {
        mScannerView = ZXingScannerView(context)
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        frame_layout_camera.addView(mScannerView)
    }

    private fun initDefaultView() {
        text_view_qr_code_value.text = "QR Code Value"
        button_pay_ticket.visibility = View.GONE
    }

    override fun onStart() {
        mScannerView.startCamera()
        doRequestPermission()
        super.onStart()
    }

    private fun doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context?.let { ContextCompat.checkSelfPermission(it,Manifest.permission.CAMERA) } != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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
        text_view_qr_code_value.text = rawResult?.text
        button_pay_ticket.visibility = View.VISIBLE
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button_pay_ticket-> {
                mScannerView.resumeCameraPreview(this)
                initDefaultView()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }


}