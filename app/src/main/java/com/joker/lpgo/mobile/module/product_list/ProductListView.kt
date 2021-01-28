package com.joker.lpgo.mobile.module.product_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.joker.lpgo.data.model.Product
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.data.remote.AppApi
import com.joker.lpgo.mobile.data.remote.endpoint.HomeApi
import com.joker.lpgo.mobile.data.remote.endpoint.ProductApi
import com.joker.lpgo.mobile.databinding.ScreenDashboardBinding
import com.joker.lpgo.mobile.databinding.ScreenProductListBinding
import com.joker.lpgo.mobile.module.product_detail.ProductDetailView
import com.joker.lpgo.ui.dashboard.adapter.NearByProductAdapter
import com.joker.lpgo.ui.dashboard.adapter.ProductAdapter
import com.joker.lpgo.ui.dashboard.adapter.RecommendedProductAdapter
import com.tapadoo.alerter.Alerter
import io.reactivex.android.schedulers.AndroidSchedulers

class ProductListView : BaseFragment() {

    private var bindingView: ScreenProductListBinding? = null
    private lateinit var adapterProduct: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenProductListBinding.inflate(inflater, container, false)
        return bindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewListener()
    }

    fun setupView() {
        adapterProduct = ProductAdapter(arrayListOf(), object : ProductAdapter.ListenerAdapter {
            override fun onClickProductItem(view: View, data: Any) {
                if (data is Product) {
                    requestDetailProduct(data.uuid)
                }
            }
        })

        context?.let {
            bindingView?.recyclerView5?.layoutManager =
                LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            bindingView?.recyclerView5?.adapter = adapterProduct
        }

        requestProduct()
    }

    fun setupViewListener() {

    }

    @SuppressLint("CheckResult")
    fun requestProduct() {
        getBaseActivity()?.isShowProgressDialog(true)
        AppApi
            .getRequest(context = context, withTokenAuth = true)
            ?.create(ProductApi::class.java)
            ?.getProduct()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { result ->
                    val data = result.body()
                    getBaseActivity()?.isShowProgressDialog(false)
                    if(result.isSuccessful) {
                        data?.data?.let { product ->
                            adapterProduct.addData(product)
                        }
                    } else {
                        Alerter.create(activity)
                            .setTitle("Failed get Product")
                            .setText("Server is busy")
                            .show()
                    }
                },
                { error ->
                    getBaseActivity()?.isShowProgressDialog(false)
                    Alerter.create(activity)
                        .setTitle("Failed get Product")
                        .setText("Server is busy")
                        .show()
                    error.printStackTrace()
                }
            )
    }

    @SuppressLint("CheckResult")
    fun requestDetailProduct(uuid: String) {
        getBaseActivity()?.isShowProgressDialog(true)
        AppApi
            .getRequest(context = context, withTokenAuth = true)
            ?.create(ProductApi::class.java)
            ?.getDetailProduct(uuid = uuid)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { result ->
                    val data = result.body()
                    getBaseActivity()?.isShowProgressDialog(false)
                    if(result.isSuccessful) {
                        data?.data?.let { product ->
                            ProductDetailView.newInstance(product).let {
                                childFragmentManager.beginTransaction().add(it, it.TAG).commit()
                            }
                        }
                    } else {
                        Alerter.create(activity)
                            .setTitle("Failed get Detail Product")
                            .setText("Server is busy")
                            .show()
                    }
                },
                { error ->
                    getBaseActivity()?.isShowProgressDialog(false)
                    Alerter.create(activity)
                        .setTitle("Failed get Detail Product")
                        .setText("Server is busy")
                        .show()
                    error.printStackTrace()
                }
            )
    }

}