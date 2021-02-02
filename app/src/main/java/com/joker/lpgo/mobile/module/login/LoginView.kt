package com.joker.lpgo.mobile.module.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.data.preference.AppPreference
import com.joker.lpgo.mobile.data.remote.AppApi
import com.joker.lpgo.mobile.data.remote.endpoint.AuthApi
import com.joker.lpgo.mobile.data.remote.request.ForgotRequest
import com.joker.lpgo.mobile.data.remote.request.LoginRequest
import com.joker.lpgo.mobile.databinding.ScreenLoginBinding
import com.joker.lpgo.mobile.module.home.HomeView
import com.tapadoo.alerter.Alerter
import io.reactivex.android.schedulers.AndroidSchedulers

class LoginView : BaseFragment() {

    private var bindingView: ScreenLoginBinding? = null
    private var loginRequest = LoginRequest()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenLoginBinding.inflate(inflater, container, false)
        return bindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewListener()
    }

    fun setupView() {
        bindingView?.textViewRegister?.setHtml(getString(R.string.register_desc))
    }

    fun setupViewListener() {
        bindingView?.textViewForgot?.setOnClickListener {
            navigatePage(R.id.forgotView, it)
        }

        bindingView?.textViewRegister?.setOnClickListener {
            navigatePage(R.id.registerView, it)
        }

        bindingView?.button2?.setOnClickListener {
            processLogin()
        }
    }

    @SuppressLint("CheckResult")
    fun processLogin() {
        if (isValidInput()) {
            getBaseActivity()?.isShowProgressDialog(true)

            AppApi
                .getRequest(context = context, withTokenAuth = false)
                ?.create(AuthApi::class.java)
                ?.postLogin(
                    username = loginRequest.username,
                    password = loginRequest.password
                )
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                    { result ->
                        getBaseActivity()?.isShowProgressDialog(false)
                        val data = result.body()
                        if(result.isSuccessful) {
                            AppPreference.setAppIsLogin(true)
                            AppPreference.setCurrentUser(data?.data?.user)
                            AppPreference.setAppToken(data?.data?.token)
                            AppPreference.setCurrentAddress(data?.data?.current_address)
                            HomeView.launchIntent(requireContext())
                            activity?.finish()
                        } else {
                            Alerter.create(activity)
                                .setTitle(data?.error?.title.toString())
                                .setText(data?.error?.message.toString())
                                .show()
                        }
                    },
                    { error ->
                        getBaseActivity()?.isShowProgressDialog(false)
                        Alerter.create(activity)
                            .setTitle("Failed login")
                            .setText("Server is busy")
                            .show()
                        error.printStackTrace()
                    }
                )
        }
    }

    fun isValidInput() : Boolean {
        if (bindingView?.editTextUsername?.getText().equals("")) {
            Alerter.create(activity)
                .setTitle("Failed login")
                .setText("Please check your email / phone")
                .show()
            bindingView?.editTextUsername?.isFocusable = true
            return false
        } else {
            loginRequest.username = bindingView?.editTextUsername?.getText().toString()
        }

        if (bindingView?.editTextPassword?.getText().equals("")) {
            Alerter.create(activity)
                .setTitle("Failed login")
                .setText("Please check your password")
                .show()
            bindingView?.editTextPassword?.isFocusable = true
            return false
        } else {
            loginRequest.password = bindingView?.editTextPassword?.getText().toString()
        }

        return true
    }

}