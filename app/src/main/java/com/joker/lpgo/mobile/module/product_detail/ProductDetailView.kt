package com.joker.lpgo.mobile.module.product_detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.github.heyalex.bottomdrawer.BottomDrawerDialog
import com.github.heyalex.bottomdrawer.BottomDrawerFragment
import com.github.heyalex.handle.PlainHandleView
import com.joker.lpgo.data.model.Product
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.data.model.Cart
import com.joker.lpgo.mobile.data.preference.AppPreference
import com.joker.lpgo.mobile.databinding.ScreenProductDetailBinding
import com.joker.lpgo.mobile.module.home.HomeView
import com.tapadoo.alerter.Alerter

class ProductDetailView: BottomDrawerFragment() {

    var TAG = "ProductDetailBottomDialog"

    private var bindingView: ScreenProductDetailBinding? = null
    private var product: Product? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            product = it.getParcelable(DATA_PRODUCT)
            Log.e(TAG, "onAttach: $product" )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingView = ScreenProductDetailBinding.inflate(inflater, container, false)
        return bindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        product?.let {
            bindingView?.textView26?.text = it.name
            bindingView?.textView27?.text = it.desc
            bindingView?.textPrice?.textView9?.text = "Rp. ${it.sale_price}"
            bindingView?.textPrice?.imageView5?.setImageResource(R.drawable.ic_dollar)
            bindingView?.txtQty?.textView9?.text = "${it.qty}"
            bindingView?.txtQty?.imageView5?.setImageResource(R.drawable.ic_stack)
            bindingView?.imageView18?.let { it1 ->
                Glide
                        .with(it1)
                        .load(it.img_cover)
                        .centerCrop()
                        .placeholder(R.drawable.ic_no_image_default)
                        .error(R.drawable.ic_no_image_default)
                        .into(it1)
            }
        }

        val tempDataCart = AppPreference.getCartUser()
        val tempCart = tempDataCart?.find { it.uuid == product?.uuid }

        if (tempCart != null) {
            bindingView?.qty?.setText("${tempCart?.qty}")
        } else {
            bindingView?.qty?.setText("1")
        }


        bindingView?.button5?.setOnClickListener {
            var qty = Integer.parseInt(bindingView?.qty?.getText())
            if (qty > 0) {
                qty -= 1
                bindingView?.qty?.setText("${qty}")
            }
        }

        bindingView?.button6?.setOnClickListener {
            var qty = Integer.parseInt(bindingView?.qty?.getText())
            qty += 1
            bindingView?.qty?.setText("${qty}")
        }

        bindingView?.button7?.setOnClickListener {
            Alerter.create(activity)
                .setTitle("Success Insert Cart")
                .setText("You are success add item into cart")
                .show()
            (activity as HomeView).addToCart()
            var qty = Integer.parseInt(bindingView?.qty?.getText())
            product?.let {
                val cart = Cart(uuid = it.uuid, product = it.name, qty = qty, price = it.sale_price, stock = it.qty, image = it.img_cover)
                AppPreference.setCartUser(cart)
                Log.e(TAG, "onViewCreated: ${AppPreference.getCartUser().toString()}" )
            }

            dismissWithBehavior()
        }
    }

    override fun configureBottomDrawer(): BottomDrawerDialog {
        return BottomDrawerDialog.build(requireContext()) {
            theme = R.style.AppBottomDialog
            //configure handle view
            handleView = PlainHandleView(context).apply {
                val widthHandle =
                    resources.getDimensionPixelSize(R.dimen.bottom_sheet_handle_width)
                val heightHandle =
                    resources.getDimensionPixelSize(R.dimen.bottom_sheet_handle_height)
                val params =
                    FrameLayout.LayoutParams(widthHandle, heightHandle, Gravity.CENTER_HORIZONTAL)

                params.topMargin =
                    resources.getDimensionPixelSize(R.dimen.bottom_sheet_handle_top_margin)

                layoutParams = params
            }
        }
    }

    companion object {
        var DATA_PRODUCT = "DATA_PRODUCT"

        fun newInstance(data: Product) : ProductDetailView {
            var fragment = ProductDetailView()
            fragment.apply {
                arguments = Bundle().apply {
                    putParcelable(DATA_PRODUCT, data)
                }
            }
            return fragment
        }
    }

}