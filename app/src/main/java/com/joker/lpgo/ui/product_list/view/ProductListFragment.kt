package com.joker.lpgo.ui.product_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.joker.lpgo.data.mock.AddressListMock
import com.joker.lpgo.databinding.ScreenProductListBinding
import com.joker.lpgo.ui.product_list.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var bindingView: ScreenProductListBinding? = null
    private lateinit var adapterProduct: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenProductListBinding.inflate(inflater, container, false)
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
        adapterProduct = ProductAdapter(arrayListOf())

        context?.let {
            bindingView?.recyclerView5?.layoutManager = LinearLayoutManager(it)
            bindingView?.recyclerView5?.adapter = adapterProduct
        }

        adapterProduct.addData(AddressListMock().data)
    }

}
