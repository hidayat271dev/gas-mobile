package com.joker.lpgo.mobile.module.order_detail

import android.annotation.SuppressLint
import android.content.Context
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
    private var orderId: String? = null

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        orderId = arguments?.getString("order_id")
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
        orderId?.let {
            AppApi
                .getRequest(context = context, withTokenAuth = true)
                ?.create(OrderApi::class.java)
                ?.getOrder(uuid = it)
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

                                var subTotal = 0
                                product?.detail.forEach {
                                    subTotal += (it.qty * it.sale_price)
                                }
                                var tax = subTotal * (0.1)
                                var del = 20000
                                var total = subTotal + del + tax

                                bindingView?.sub?.text = "${subTotal}"
                                bindingView?.del?.text = "${del}"
                                bindingView?.tax?.text = "${tax}"
                                bindingView?.total?.text = "${total}"

                                if (product.order_status.equals("1")) {
                                    bindingView?.status?.textView9?.text = "Confrim"
                                } else {
                                    bindingView?.status?.textView9?.text = "Pending"
                                }
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

}