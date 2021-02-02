package com.joker.lpgo.mobile.module.dashboard

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.joker.lpgo.data.model.Product
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.data.model.User
import com.joker.lpgo.mobile.data.preference.AppPreference
import com.joker.lpgo.mobile.data.remote.AppApi
import com.joker.lpgo.mobile.data.remote.endpoint.HomeApi
import com.joker.lpgo.mobile.databinding.ScreenDashboardBinding
import com.joker.lpgo.mobile.module.home.HomeView
import com.joker.lpgo.mobile.module.product_detail.ProductDetailView
import com.joker.lpgo.mobile.util.extension.getAddressInfo
import com.joker.lpgo.mobile.util.extension.show
import com.joker.lpgo.ui.dashboard.adapter.CategoryAdapter
import com.joker.lpgo.ui.dashboard.adapter.NearByProductAdapter
import com.joker.lpgo.ui.dashboard.adapter.RecommendedProductAdapter
import com.tapadoo.alerter.Alerter
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*


class DashboardView : BaseFragment() {

    var TAG = "DashboardView"
    private var bindingView: ScreenDashboardBinding? = null
    private lateinit var adapterNearByProduct: NearByProductAdapter
    private lateinit var adapterCategory: CategoryAdapter
    private lateinit var adapterRecommendedProduct: RecommendedProductAdapter
    private lateinit var currentUser: User

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenDashboardBinding.inflate(inflater, container, false)
        return bindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewListener()
    }

    fun setupView() {
        (activity as HomeView).bindingView.containerBottom.show(true)

        AppPreference.getCurrentUser()?.let {
            currentUser = it
            bindingView?.profileImage2?.let { it1 ->
                Log.e(TAG, "setupView: $currentUser")
                Glide
                    .with(requireContext())
                    .load(currentUser.pic_profile)
                    .centerCrop()
                    .placeholder(R.drawable.ic_no_image_default)
                    .error(R.drawable.ic_no_image_default)
                    .into(it1)
            }
        }

        adapterNearByProduct = NearByProductAdapter(arrayListOf(), object : NearByProductAdapter.ListenerAdapter {
            override fun onClickNearByItem(view: View, data: Any) {
                if (data is Product) {
                    requestDetailProduct(data.uuid)
                }
            }
        })
        adapterCategory = CategoryAdapter(requireContext(), arrayListOf(), object : CategoryAdapter.ListenerAdapter {
            override fun onClickCategoryItem(view: View) {

            }
        })
        adapterRecommendedProduct = RecommendedProductAdapter(arrayListOf(), object : RecommendedProductAdapter.ListenerAdapter {
            override fun onClickRecomendedItem(view: View, data: Any) {
                if (data is Product) {
                    requestDetailProduct(data.uuid)
                }
            }

        })
        context?.let {
            bindingView?.recyclerViewNearBy?.layoutManager = LinearLayoutManager(it, LinearLayoutManager.HORIZONTAL, false)
            bindingView?.recyclerViewNearBy?.adapter = adapterNearByProduct

            bindingView?.recyclerCategory?.layoutManager = LinearLayoutManager(it, LinearLayoutManager.HORIZONTAL, false)
            bindingView?.recyclerCategory?.adapter = adapterCategory

            bindingView?.recyclerRecommended?.layoutManager = LinearLayoutManager(it, LinearLayoutManager.HORIZONTAL, false)
            bindingView?.recyclerRecommended?.adapter = adapterRecommendedProduct

            setAddress()
        }

        requestDataNearBy()
    }

    fun setAddress() {
        context?.let {
            val address = AppPreference.getCurrentAddress()
            if (address == null) {
                bindingView?.textView15?.text = "".getAddressInfo(it, -6.8828687, 107.5836387)
            } else {
                bindingView?.textView15?.text = "".getAddressInfo(it, address.lat, address.long)
            }
        }
    }

    fun setupViewListener() {
        bindingView?.profileImage2?.setOnClickListener {
            navigatePageLeft(R.id.profileView, it)
        }

        bindingView?.containerLocation?.setOnClickListener {
            navigatePageLeft(R.id.addressListView, it)
        }

        bindingView?.containerPopularNearYou?.setOnClickListener {
            navigatePageLeft(R.id.productListView, it)
        }

        bindingView?.containerCategories?.setOnClickListener {
            navigatePageLeft(R.id.categoryView, it)
        }

        bindingView?.containerRecommended?.setOnClickListener {
            navigatePageLeft(R.id.productListView, it)
        }
    }

    @SuppressLint("CheckResult")
    fun requestDataNearBy() {
        getBaseActivity()?.isShowProgressDialog(true)

        AppApi
            .getRequest(context = context, withTokenAuth = false)
            ?.create(HomeApi::class.java)
            ?.getNearBy()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                    { result ->
                        val data = result.body()
                        if (result.isSuccessful) {
                            data?.data?.let { adapterNearByProduct.addData(it) }
                            requestDataCategories()
                        } else {
                            getBaseActivity()?.isShowProgressDialog(false)
                            Alerter.create(activity)
                                    .setTitle("Failed get NearBy")
                                    .setText("Server is busy")
                                    .show()
                        }
                    },
                    { error ->
                        getBaseActivity()?.isShowProgressDialog(false)
                        Alerter.create(activity)
                                .setTitle("Failed get NearBy")
                                .setText("Server is busy")
                                .show()
                        error.printStackTrace()
                    }
            )
    }

    @SuppressLint("CheckResult")
    fun requestDataCategories() {
        AppApi
            .getRequest(context = context, withTokenAuth = false)
            ?.create(HomeApi::class.java)
            ?.getCategories()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                    { result ->
                        val data = result.body()
                        if (result.isSuccessful) {
                            data?.data?.let { adapterCategory.addData(it) }
                            requestDataRecommended()
                        } else {
                            getBaseActivity()?.isShowProgressDialog(false)
                            Alerter.create(activity)
                                    .setTitle("Failed get Categories")
                                    .setText("Server is busy")
                                    .show()
                        }
                    },
                    { error ->
                        getBaseActivity()?.isShowProgressDialog(false)
                        Alerter.create(activity)
                                .setTitle("Failed get Categories")
                                .setText("Server is busy")
                                .show()
                        error.printStackTrace()
                    }
            )
    }

    @SuppressLint("CheckResult")
    fun requestDataRecommended() {
        AppApi
            .getRequest(context = context, withTokenAuth = false)
            ?.create(HomeApi::class.java)
            ?.getRecommended()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                    { result ->
                        val data = result.body()
                        getBaseActivity()?.isShowProgressDialog(false)
                        if (result.isSuccessful) {
                            data?.data?.let { adapterRecommendedProduct.addData(it) }
                        } else {
                            Alerter.create(activity)
                                    .setTitle("Failed get Recommended")
                                    .setText("Server is busy")
                                    .show()
                        }
                    },
                    { error ->
                        getBaseActivity()?.isShowProgressDialog(false)
                        Alerter.create(activity)
                                .setTitle("Failed get Recommended")
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
            .getRequest(context = context, withTokenAuth = false)
            ?.create(HomeApi::class.java)
            ?.getDetailProduct(uuid = uuid)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                    { result ->
                        val data = result.body()
                        getBaseActivity()?.isShowProgressDialog(false)
                        if (result.isSuccessful) {
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