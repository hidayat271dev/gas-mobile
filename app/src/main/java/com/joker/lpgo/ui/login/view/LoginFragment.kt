package com.joker.lpgo.ui.login.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.joker.lpgo.R
import com.joker.lpgo.data.sharepreference.AppSharedPreference
import com.joker.lpgo.databinding.ScreenDashboardBinding
import com.joker.lpgo.databinding.ScreenLoginBinding
import com.joker.lpgo.ui.home.view.HomeActivity
import com.joker.lpgo.ui.main.view.MainActivity
import com.tapadoo.alerter.Alerter
import dagger.hilt.android.AndroidEntryPoint
import io.easyprefs.Prefs

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

        bindingView?.button2?.setOnClickListener {
            when {
                bindingView?.editTextUsername?.getText() != null && bindingView?.editTextPassword?.getText() != null -> {
                    if (bindingView?.editTextUsername?.getText().equals("testing") && bindingView?.editTextUsername?.getText().equals("testing")) {
                        context?.let {
                            HomeActivity.launchIntent(it)
                            AppSharedPreference.instance.isAppLogin(true)
                            activity?.finish()
                        }
                    } else {
                        Alerter.create(activity)
                                .setTitle("Failed login")
                                .setText("Please check your username or password")
                                .show()
                    }
                }
            }
        }

        bindingView?.editTextUsername?.setText("testing")
        bindingView?.editTextPassword?.setText("testing")
    }

}