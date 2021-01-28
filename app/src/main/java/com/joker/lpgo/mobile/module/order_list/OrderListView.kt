package com.joker.lpgo.mobile.module.order_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.data.model.Order
import com.joker.lpgo.mobile.data.remote.AppApi
import com.joker.lpgo.mobile.data.remote.endpoint.CategoryApi
import com.joker.lpgo.mobile.data.remote.endpoint.OrderApi
import com.joker.lpgo.mobile.databinding.ScreenCategoryBinding
import com.joker.lpgo.mobile.databinding.ScreenOrderListBinding
import com.joker.lpgo.mobile.module.order_list.adapter.OrderAdapter
import com.joker.lpgo.ui.category.adapter.CategoryAdapter
import com.tapadoo.alerter.Alerter
import io.reactivex.android.schedulers.AndroidSchedulers

class OrderListView : BaseFragment() {

    private var bindingView: ScreenOrderListBinding? = null
    private lateinit var adapterOrder: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenOrderListBinding.inflate(inflater, container, false)
        return bindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewListener()
    }

    fun setupView() {
        adapterOrder = OrderAdapter(requireContext(), arrayListOf(), object : OrderAdapter.ListenerAdapter {
            override fun onClickOrderItem(view: View, data: Any) {
                if (data is Order) {
                    navigatePageLeft(R.id.orderDetailView, view)
                }
            }

        })

        context?.let {
            bindingView?.recyclerView5?.layoutManager = LinearLayoutManager(requireContext())
            bindingView?.recyclerView5?.adapter = adapterOrder
        }

        requestOrder()
    }

    fun setupViewListener() {

    }

    @SuppressLint("CheckResult")
    fun requestOrder() {
        getBaseActivity()?.isShowProgressDialog(true)
        AppApi
            .getRequest(context = context, withTokenAuth = true)
            ?.create(OrderApi::class.java)
            ?.getOrder()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { result ->
                    val data = result.body()
                    getBaseActivity()?.isShowProgressDialog(false)
                    if(result.isSuccessful) {
                        data?.data?.let { product ->
                            adapterOrder.addData(product)
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