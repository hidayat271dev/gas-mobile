package com.joker.lpgo.ui.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
import com.joker.lpgo.ui.dashboard.viewmodel.DashboardViewModel
import com.joker.lpgo.ui.main.viewmodel.MainViewModel
import com.joker.lpgo.ui.product_detail.view.ProductDetailBottomDialog
import com.joker.lpgo.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var bindingView: ScreenDashboardBinding? = null
    private val viewModel : DashboardViewModel by viewModels()

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
            setupObserver()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingView = null
    }

    fun setupView() {
        bindingView?.textView15?.text = "Jl Sarimanis 7 Blok 13 No 78 Bandung 40151"

        adapterNearByProduct = NearByProductAdapter(arrayListOf(), object : NearByProductAdapter.ListenerAdapter {
            override fun onClickNearByItem(view: View) {
                ProductDetailBottomDialog.newInstance().let {
                    childFragmentManager.beginTransaction().add(it, it.TAG).commit()
                }
            }
        })
        adapterCategory = CategoryAdapter(requireContext(), arrayListOf(), object : CategoryAdapter.ListenerAdapter {
            override fun onClickCategoryItem(view: View) {
                Navigation.findNavController(view).navigate(R.id.action_dashboardFragment_to_productListFragment)
            }
        })
        adapterRecommendedProduct = RecommendedProductAdapter(arrayListOf(), object : RecommendedProductAdapter.ListenerAdapter {
            override fun onClickRecomendedItem(view: View) {
                ProductDetailBottomDialog.newInstance().let {
                    childFragmentManager.beginTransaction().add(it, it.TAG).commit()
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
        }

        bindingView?.profileImage2?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_dashboardFragment_to_profileFragment)
        }

        bindingView?.containerLocation?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_dashboardFragment_to_currentLocationFragment)
        }

        bindingView?.containerPopularNearYou?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_dashboardFragment_to_productListFragment)
        }

        bindingView?.containerCategories?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_dashboardFragment_to_categoryListFragment)
        }

        bindingView?.containerRecommended?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_dashboardFragment_to_productListFragment)
        }

        adapterCategory.addData(
            CategoryListMock().data
        )
    }

    private fun setupObserver() {
        viewModel.products.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data ->
                        adapterRecommendedProduct.addData(data)
                        adapterNearByProduct.addData(data)
                    }
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                }
            }
        })
    }

}
