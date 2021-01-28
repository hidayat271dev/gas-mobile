package com.joker.lpgo.mobile.module.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.data.model.Cart
import com.joker.lpgo.mobile.data.preference.AppPreference
import com.joker.lpgo.mobile.databinding.ScreenCartBinding
import com.joker.lpgo.mobile.module.home.HomeView
import com.joker.lpgo.mobile.util.extension.show
import com.joker.lpgo.ui.dashboard.adapter.CartAdapter

class CartView : BaseFragment() {

    private var bindingView: ScreenCartBinding? = null
    private lateinit var cartAdapter: CartAdapter
    private var dataCart: List<Cart> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenCartBinding.inflate(inflater, container, false)
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
        (activity as HomeView).bindingView.containerBottom.show(false)

        cartAdapter = CartAdapter(requireContext(), arrayListOf(), object : CartAdapter.ListenerAdapter {
            override fun removeItemCart(data: Any) {
                if (data is Cart) {
                    AppPreference.setRemoveCart(data)
                    getDataCart()
                }
            }

            override fun incDecItemCart(data: Any, isIncrement: Boolean) {
                if (data is Cart) {
                    if (isIncrement) data.qty += 1
                    else data.qty -= 1
                    AppPreference.setCartUser(data)
                    getDataCart()
                }
            }
        })

        context?.let {
            bindingView?.recyclerView4?.layoutManager =
                LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            bindingView?.recyclerView4?.adapter = cartAdapter
        }

        getDataCart()

        bindingView?.button8?.setOnClickListener {
            navigatePageLeft(R.id.checkoutView, it)
        }
    }

    fun getDataCart() {
        AppPreference.getCartUser()?.let {
            dataCart = it
            cartAdapter.addData(dataCart)
        }
    }

}