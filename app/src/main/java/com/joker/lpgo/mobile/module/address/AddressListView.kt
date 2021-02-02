package com.joker.lpgo.mobile.module.address

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.data.model.Address
import com.joker.lpgo.mobile.data.model.Order
import com.joker.lpgo.mobile.data.preference.AppPreference
import com.joker.lpgo.mobile.data.remote.AppApi
import com.joker.lpgo.mobile.data.remote.endpoint.AddressApi
import com.joker.lpgo.mobile.data.remote.endpoint.OrderApi
import com.joker.lpgo.mobile.databinding.ScreenAddressListBinding
import com.joker.lpgo.mobile.databinding.ScreenOrderListBinding
import com.joker.lpgo.mobile.module.order_list.adapter.AddressAdapter
import com.joker.lpgo.mobile.module.order_list.adapter.OrderAdapter
import com.tapadoo.alerter.Alerter
import io.reactivex.android.schedulers.AndroidSchedulers

class AddressListView : BaseFragment() {

    private var bindingView: ScreenAddressListBinding? = null
    private lateinit var adapterAddress: AddressAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenAddressListBinding.inflate(inflater, container, false)
        return bindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewListener()
    }

    fun setupView() {
        adapterAddress = AddressAdapter(requireContext(), arrayListOf(), object : AddressAdapter.ListenerAdapter {
            override fun onClickAddressItem(view: View, data: Any) {
                if (data is Address) {
                    updatePrimaryAddress(data)
                }
            }

        })

        context?.let {
            bindingView?.recyclerView5?.layoutManager = LinearLayoutManager(requireContext())
            bindingView?.recyclerView5?.adapter = adapterAddress
        }

        requestOrder()
    }

    fun setupViewListener() {
        bindingView?.floatingActionButton?.setOnClickListener {
            navigatePageLeft(R.id.currentLocation, it)
        }
    }

    @SuppressLint("CheckResult")
    fun requestOrder() {
        getBaseActivity()?.isShowProgressDialog(true)
        AppApi
                .getRequest(context = context, withTokenAuth = true)
                ?.create(AddressApi::class.java)
                ?.getAddress()
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { result ->
                            val data = result.body()
                            getBaseActivity()?.isShowProgressDialog(false)
                            if(result.isSuccessful) {
                                data?.data?.let { product ->
                                    adapterAddress.addData(product)
                                }
                            } else {
                                Alerter.create(activity)
                                        .setTitle("Failed get address list")
                                        .setText("Server is busy")
                                        .show()
                            }
                        },
                        { error ->
                            getBaseActivity()?.isShowProgressDialog(false)
                            Alerter.create(activity)
                                    .setTitle("Failed get address list")
                                    .setText("Server is busy")
                                    .show()
                            error.printStackTrace()
                        }
                )
    }

    @SuppressLint("CheckResult")
    fun updatePrimaryAddress(data: Address) {
        getBaseActivity()?.isShowProgressDialog(true)
        AppApi
                .getRequest(context = context, withTokenAuth = true)
                ?.create(AddressApi::class.java)
                ?.updatePrimaryAddress(
                        uuid = data.uuid
                )
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { result ->
                            val data = result.body()
                            getBaseActivity()?.isShowProgressDialog(false)
                            if(result.isSuccessful) {
                                AppPreference.setCurrentAddress(data?.data)
                                Alerter.create(activity)
                                        .setTitle("Success update address")
                                        .setText("Success Update")
                                        .setOnHideListener{
                                            requestOrder()
                                        }
                                        .show()
                            } else {
                                Alerter.create(activity)
                                        .setTitle("Failed get address list")
                                        .setText("Server is busy")
                                        .show()
                            }
                        },
                        { error ->
                            getBaseActivity()?.isShowProgressDialog(false)
                            Alerter.create(activity)
                                    .setTitle("Failed get address list")
                                    .setText("Server is busy")
                                    .show()
                            error.printStackTrace()
                        }
                )
    }
}