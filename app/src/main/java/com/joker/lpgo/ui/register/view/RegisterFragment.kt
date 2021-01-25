package com.joker.lpgo.ui.register.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.joker.lpgo.R
import com.joker.lpgo.databinding.ScreenLoginBinding
import com.joker.lpgo.databinding.ScreenRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var bindingView: ScreenRegisterBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenRegisterBinding.inflate(inflater, container, false)
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
        bindingView?.textViewRegister2?.setHtml(getString(R.string.register_tnc))
        bindingView?.textViewRegister3?.setHtml(getString(R.string.login_desc))

        bindingView?.textViewRegister3?.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

}