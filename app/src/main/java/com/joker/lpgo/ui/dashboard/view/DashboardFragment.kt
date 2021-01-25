package com.joker.lpgo.ui.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.joker.lpgo.R
import com.joker.lpgo.data.mock.CategoryListMock
import com.joker.lpgo.data.mock.ProductNearByListMock
import com.joker.lpgo.data.mock.ProductRecommendedListMock
import com.joker.lpgo.databinding.ScreenDashboardBinding
import com.joker.lpgo.ui.dashboard.adapter.CategoryAdapter
import com.joker.lpgo.ui.dashboard.adapter.NearByProductAdapter
import com.joker.lpgo.ui.dashboard.adapter.RecommendedProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var bindingView: ScreenDashboardBinding? = null
    private lateinit var adapterNearByProduct: NearByProductAdapter
    private lateinit var adapterCategory: CategoryAdapter
    private lateinit var adapterRecommendedProduct: RecommendedProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenDashboardBinding.inflate(inflater, container, false)
        val view = bindingView?.root
        if (view != null) {
            setupView()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingView = null
    }

    fun setupView() {
        adapterNearByProduct = NearByProductAdapter(arrayListOf())
        adapterCategory = CategoryAdapter(arrayListOf())
        adapterRecommendedProduct = RecommendedProductAdapter(arrayListOf())

        context?.let {
            bindingView?.recyclerViewNearBy?.layoutManager = LinearLayoutManager(it, LinearLayoutManager.HORIZONTAL, false)
            bindingView?.recyclerViewNearBy?.adapter = adapterNearByProduct

            bindingView?.recyclerCategory?.layoutManager = LinearLayoutManager(it, LinearLayoutManager.HORIZONTAL, false)
            bindingView?.recyclerCategory?.adapter = adapterCategory

            bindingView?.recyclerRecommended?.layoutManager = LinearLayoutManager(it, LinearLayoutManager.HORIZONTAL, false)
            bindingView?.recyclerRecommended?.adapter = adapterRecommendedProduct
        }

        bindingView?.profileImage2?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_dashboardFragment_to_profileFragment)
        }


        adapterNearByProduct.addData(
            ProductNearByListMock().data
        )

        adapterCategory.addData(
            CategoryListMock().data
        )

        adapterRecommendedProduct.addData(
            ProductRecommendedListMock().data
        )
    }

}
