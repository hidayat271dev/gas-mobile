package com.joker.lpgo.ui.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.joker.lpgo.R
import com.joker.lpgo.databinding.ScreenDashboardBinding
import com.joker.lpgo.databinding.ScreenLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var bindingView: ScreenLoginBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenLoginBinding.inflate(inflater, container, false)
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
        bindingView?.textViewRegister?.setHtml(getString(R.string.register_desc))

        bindingView?.textViewForgot?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_forgotFragment)
        }

        bindingView?.textViewRegister?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}