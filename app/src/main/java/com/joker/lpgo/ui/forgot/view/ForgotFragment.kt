package com.joker.lpgo.ui.forgot.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.joker.lpgo.R
import com.joker.lpgo.databinding.ScreenForgotBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment : Fragment() {

    private var bindingView: ScreenForgotBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenForgotBinding.inflate(inflater, container, false)
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
        bindingView?.textViewRegister4?.setHtml(getString(R.string.forgot_desc))

        bindingView?.textViewRegister4?.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

}