package com.joker.lpgo.mobile.module.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.databinding.ScreenAboutBinding

class AboutView : BaseFragment() {

    private var bindingView: ScreenAboutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenAboutBinding.inflate(inflater, container, false)
        val view = bindingView?.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingView = null
    }

}