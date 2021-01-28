package com.joker.lpgo.mobile.module.forgot

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.data.remote.AppApi
import com.joker.lpgo.mobile.data.remote.endpoint.AuthApi
import com.joker.lpgo.mobile.data.remote.request.ForgotRequest
import com.joker.lpgo.mobile.data.remote.request.RegisterRequest
import com.joker.lpgo.mobile.databinding.ScreenForgotBinding
import com.joker.lpgo.mobile.databinding.ScreenRegisterBinding
import com.tapadoo.alerter.Alerter
import io.reactivex.android.schedulers.AndroidSchedulers

class ForgotView : BaseFragment() {

    private var bindingView: ScreenForgotBinding? = null
    private var forgotRequest = ForgotRequest()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenForgotBinding.inflate(inflater, container, false)
        return bindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewListener()
    }

    fun setupView() {
        bindingView?.textViewRegister4?.setHtml(getString(R.string.forgot_desc))
    }

    fun setupViewListener() {
        bindingView?.textViewRegister4?.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        bindingView?.button3?.setOnClickListener {
            processForgot()
        }
    }

    @SuppressLint("CheckResult")
    fun processForgot() {
        if (isValidInput()) {
            getBaseActivity()?.isShowProgressDialog(true)

            AppApi
                .getRequest(context = context, withTokenAuth = false)
                ?.create(AuthApi::class.java)
                ?.postForgot(
                    email = forgotRequest.email
                )
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                    { result ->
                        getBaseActivity()?.isShowProgressDialog(false)
                        val data = result.body()
                        if(result.isSuccessful) {
                            Alerter.create(activity)
                                .setTitle("Success forgot")
                                .setText("Please login for active account")
                                .show()
                        } else {
                            Alerter.create(activity)
                                .setTitle("Failed forgot")
                                .setText("Your failed to forgot account")
                                .show()
                        }
                    },
                    { error ->
                        getBaseActivity()?.isShowProgressDialog(false)
                        Alerter.create(activity)
                            .setTitle("Failed forgot")
                            .setText("Server is busy")
                            .show()
                        error.printStackTrace()
                    }
                )
        }
    }

    fun isValidInput() : Boolean {
        if (bindingView?.email?.getText().equals("")) {
            Alerter.create(activity)
                .setTitle("Failed forgot")
                .setText("Please check your email")
                .show()
            bindingView?.email?.isFocusable = true
            return false
        } else {
            forgotRequest.email = bindingView?.email?.getText().toString()
        }

        return true
    }

}