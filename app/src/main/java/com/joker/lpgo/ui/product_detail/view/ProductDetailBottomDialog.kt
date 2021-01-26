package com.joker.lpgo.ui.product_detail.view

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.github.heyalex.bottomdrawer.BottomDrawerDialog
import com.github.heyalex.bottomdrawer.BottomDrawerFragment
import com.github.heyalex.handle.PlainHandleView
import com.joker.lpgo.R
import com.joker.lpgo.databinding.ScreenLoginBinding
import com.joker.lpgo.databinding.ScreenProductDetailBinding

class ProductDetailBottomDialog: BottomDrawerFragment() {

    var TAG = "ProductDetailBottomDialog"

    private var bindingView: ScreenProductDetailBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingView = ScreenProductDetailBinding.inflate(inflater, container, false)
        val view = bindingView?.root
        if (view != null) {
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        fun newInstance() : ProductDetailBottomDialog {
            var fragment = ProductDetailBottomDialog()
            fragment.apply {
                arguments = Bundle().apply {

                }
            }
            return fragment
        }
    }

}