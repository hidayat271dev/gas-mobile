package com.joker.lpgo.ui.product_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joker.lpgo.databinding.ScreenCurrrentLocationBinding
import com.joker.lpgo.databinding.ScreenDashboardBinding
import com.joker.lpgo.databinding.ScreenProductListBinding
import com.joker.lpgo.databinding.ScreenProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var bindingView: ScreenProductListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenProductListBinding.inflate(inflater, container, false)
        val view = bindingView?.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingView = null
    }

}
