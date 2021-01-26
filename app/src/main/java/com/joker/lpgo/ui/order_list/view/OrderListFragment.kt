package com.joker.lpgo.ui.order_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.joker.lpgo.data.mock.AddressListMock
import com.joker.lpgo.databinding.ScreenOrderListBinding
import com.joker.lpgo.databinding.ScreenProfileBinding
import com.joker.lpgo.ui.order_list.adapter.OrderAdapter
import com.joker.lpgo.ui.product_list.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListFragment : Fragment() {

    private var bindingView: ScreenOrderListBinding? = null
    private lateinit var adapterOrder: OrderAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenOrderListBinding.inflate(inflater, container, false)
        val view = bindingView?.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingView = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    fun setupView() {
        adapterOrder = OrderAdapter(arrayListOf())

        context?.let {
            bindingView?.recyclerView5?.layoutManager = LinearLayoutManager(it)
            bindingView?.recyclerView5?.adapter = adapterOrder
        }

        adapterOrder.addData(AddressListMock().data)
    }
}