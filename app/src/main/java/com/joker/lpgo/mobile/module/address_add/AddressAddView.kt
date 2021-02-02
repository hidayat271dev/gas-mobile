package com.joker.lpgo.mobile.module.address_add

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.github.heyalex.bottomdrawer.BottomDrawerDialog
import com.github.heyalex.bottomdrawer.BottomDrawerFragment
import com.github.heyalex.handle.PlainHandleView
import com.joker.lpgo.data.model.Product
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseActivity
import com.joker.lpgo.mobile.data.model.Cart
import com.joker.lpgo.mobile.data.preference.AppPreference
import com.joker.lpgo.mobile.data.remote.AppApi
import com.joker.lpgo.mobile.data.remote.endpoint.AddressApi
import com.joker.lpgo.mobile.data.remote.endpoint.AuthApi
import com.joker.lpgo.mobile.databinding.ScreenAddAddressBinding
import com.joker.lpgo.mobile.databinding.ScreenProductDetailBinding
import com.joker.lpgo.mobile.module.home.HomeView
import com.joker.lpgo.mobile.module.product_detail.ProductDetailView
import com.joker.lpgo.mobile.util.extension.getAddressInfo
import com.tapadoo.alerter.Alerter
import io.reactivex.android.schedulers.AndroidSchedulers

class AddressAddView: BottomDrawerFragment() {

    var TAG = "AddressAddView"

    private var bindingView: ScreenAddAddressBinding? = null
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            latitude = it.getDouble(DATA_LATITUDE)
            longitude = it.getDouble(DATA_LONGITUDE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingView = ScreenAddAddressBinding.inflate(inflater, container, false)
        return bindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingView?.textView38?.text = "".getAddressInfo(requireContext(), latitude!!, longitude!!)

        bindingView?.button10?.setOnClickListener {
            saveData()
        }
    }

    override fun configureBottomDrawer(): BottomDrawerDialog {
        return BottomDrawerDialog.build(requireContext()) {
            theme = R.style.AppBottomDialog
            //configure handle view
            handleView = PlainHandleView(context).apply {
                val widthHandle =
                        resources.getDimensionPixelSize(R.dimen.bottom_sheet_handle_width)
                val heightHandle =
                        resources.getDimensionPixelSize(R.dimen.bottom_sheet_handle_height)
                val params =
                        FrameLayout.LayoutParams(widthHandle, heightHandle, Gravity.CENTER_HORIZONTAL)

                params.topMargin =
                        resources.getDimensionPixelSize(R.dimen.bottom_sheet_handle_top_margin)

                layoutParams = params
            }
        }
    }

    @SuppressLint("CheckResult")
    fun saveData() {
        (activity as BaseActivity).isShowProgressDialog(true)

        AppApi
            .getRequest(context = context, withTokenAuth = true)
            ?.create(AddressApi::class.java)
            ?.saveAddress(
                    name = bindingView?.editTextUsername?.getText().toString(),
                    lat = latitude!!,
                    long = longitude!!
            )
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                    { result ->
                        (activity as BaseActivity).isShowProgressDialog(false)
                        val data = result.body()
                        if(result.isSuccessful) {
                            Alerter.create(activity)
                                    .setTitle("Success to save")
                                    .setText("Address was add")
                                    .show()
                            dismissWithBehavior()
                        } else {
                            Alerter.create(activity)
                                    .setTitle(data?.error?.title.toString())
                                    .setText(data?.error?.message.toString())
                                    .show()
                        }
                    },
                    { error ->
                        (activity as BaseActivity).isShowProgressDialog(false)
                        Alerter.create(activity)
                                .setTitle("Failed to save")
                                .setText("Server is busy")
                                .show()
                        error.printStackTrace()
                    }
            )
    }

    companion object {
        var DATA_LATITUDE = "DATA_LATITUDE"
        var DATA_LONGITUDE = "DATA_LONGITUDE"

        fun newInstance(latitude: Double, longitude: Double) : AddressAddView {
            var fragment = AddressAddView()
            fragment.apply {
                arguments = Bundle().apply {
                    putDouble(DATA_LATITUDE, latitude)
                    putDouble(DATA_LONGITUDE, longitude)
                }
            }
            return fragment
        }
    }

}