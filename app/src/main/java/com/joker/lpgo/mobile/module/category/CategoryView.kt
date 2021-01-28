package com.joker.lpgo.mobile.module.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.data.remote.AppApi
import com.joker.lpgo.mobile.data.remote.endpoint.CategoryApi
import com.joker.lpgo.mobile.data.remote.endpoint.ProductApi
import com.joker.lpgo.mobile.databinding.ScreenCategoryBinding
import com.joker.lpgo.mobile.module.product_detail.ProductDetailView
import com.joker.lpgo.ui.category.adapter.CategoryAdapter
import com.tapadoo.alerter.Alerter
import io.reactivex.android.schedulers.AndroidSchedulers

class CategoryView : BaseFragment() {

    private var bindingView: ScreenCategoryBinding? = null
    private lateinit var adapterCategory: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenCategoryBinding.inflate(inflater, container, false)
        return bindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewListener()
    }

    fun setupView() {
        adapterCategory = CategoryAdapter(requireContext(), arrayListOf(), object : CategoryAdapter.ListenerAdapter {
            override fun onClickCategoryItem(view: View) {
                navigatePageLeft(R.id.productListView, view)
            }

        })

        context?.let {
            bindingView?.recyclerView3?.layoutManager = GridLayoutManager(requireContext(), 4)
            bindingView?.recyclerView3?.adapter = adapterCategory
        }

        requestCategory()
    }

    fun setupViewListener() {

    }

    @SuppressLint("CheckResult")
    fun requestCategory() {
        getBaseActivity()?.isShowProgressDialog(true)
        AppApi
            .getRequest(context = context, withTokenAuth = true)
            ?.create(CategoryApi::class.java)
            ?.getCategories()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { result ->
                    val data = result.body()
                    getBaseActivity()?.isShowProgressDialog(false)
                    if(result.isSuccessful) {
                        data?.data?.let { product ->
                            adapterCategory.addData(product)
                        }
                    } else {
                        Alerter.create(activity)
                            .setTitle("Failed get category")
                            .setText("Server is busy")
                            .show()
                    }
                },
                { error ->
                    getBaseActivity()?.isShowProgressDialog(false)
                    Alerter.create(activity)
                        .setTitle("Failed get category")
                        .setText("Server is busy")
                        .show()
                    error.printStackTrace()
                }
            )
    }

}