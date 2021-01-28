package com.joker.lpgo.mobile.module.order_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.data.remote.AppApi
import com.joker.lpgo.mobile.data.remote.endpoint.OrderApi
import com.joker.lpgo.mobile.databinding.ScreenOrderDetailBinding
import com.tapadoo.alerter.Alerter
import io.reactivex.android.schedulers.AndroidSchedulers

class OrderDetailView : BaseFragment(), OnMapReadyCallback {

    private var bindingView: ScreenOrderDetailBinding? = null

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenOrderDetailBinding.inflate(inflater, container, false)
        val view = bindingView?.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingView = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestOrderDetail()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            mMap = it
        }
    }

    @SuppressLint("CheckResult")
    fun requestOrderDetail() {
        getBaseActivity()?.isShowProgressDialog(true)
        AppApi
            .getRequest(context = context, withTokenAuth = true)
            ?.create(OrderApi::class.java)
            ?.getOrder(uuid = "fafa8106-f98b-4cba-bd32-0ea345c44c6b")
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { result ->
                    val data = result.body()
                    getBaseActivity()?.isShowProgressDialog(false)
                    if(result.isSuccessful) {
                        data?.data?.let { product ->
                            bindingView?.mapView2?.getMapAsync(this)
                            bindingView?.on?.text = product.order_number
                            bindingView?.od?.text = product.created_at
                        }
                    } else {
                        Alerter.create(activity)
                            .setTitle("Failed get order list")
                            .setText("Server is busy")
                            .show()
                    }
                },
                { error ->
                    getBaseActivity()?.isShowProgressDialog(false)
                    Alerter.create(activity)
                        .setTitle("Failed get order list")
                        .setText("Server is busy")
                        .show()
                    error.printStackTrace()
                }
            )

    }

}