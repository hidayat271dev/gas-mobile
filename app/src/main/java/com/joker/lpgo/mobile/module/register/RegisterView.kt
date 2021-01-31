package com.joker.lpgo.mobile.module.register

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
import com.joker.lpgo.mobile.data.remote.request.RegisterRequest
import com.joker.lpgo.mobile.databinding.ScreenRegisterBinding
import com.tapadoo.alerter.Alerter
import io.reactivex.android.schedulers.AndroidSchedulers

class RegisterView : BaseFragment() {

    private var bindingView: ScreenRegisterBinding? = null
    private var registerRequest = RegisterRequest()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenRegisterBinding.inflate(inflater, container, false)
        return bindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewListener()
    }

    fun setupView() {
        bindingView?.textViewRegister2?.setHtml(getString(R.string.register_tnc))
        bindingView?.textViewRegister3?.setHtml(getString(R.string.login_desc))
    }

    fun setupViewListener() {
        bindingView?.textViewRegister3?.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
        
        bindingView?.button3?.setOnClickListener { 
            processRegister()
        }
    }

    @SuppressLint("CheckResult")
    fun processRegister() {
        if (isValidInput()) {
            getBaseActivity()?.isShowProgressDialog(true)

            AppApi
                .getRequest(context = context, withTokenAuth = false)
                ?.create(AuthApi::class.java)
                ?.postRegister(
                    fullname = registerRequest.fullname,
                    email = registerRequest.email,
                    phone = registerRequest.phone,
                    password = registerRequest.password
                )
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                    { result ->
                        getBaseActivity()?.isShowProgressDialog(false)
                        val data = result.body()
                        if(result.isSuccessful) {
                            Alerter.create(activity)
                                .setTitle("Success register")
                                .setText("Please login for active account")
                                .show()
                        } else {
                            Alerter.create(activity)
                                .setTitle("Success register")
                                .setText("Please login for active account")
                                .show()
                        }
                    },
                    { error ->
                        getBaseActivity()?.isShowProgressDialog(false)
                        Alerter.create(activity)
                            .setTitle("Failed register")
                            .setText("Server is busy")
                            .show()
                        error.printStackTrace()
                    }
                )
        }
    }
    
    fun isValidInput() : Boolean {
        if (bindingView?.fullname?.getText().equals("")) {
            Alerter.create(activity)
                .setTitle("Failed register")
                .setText("Please check your fullname")
                .show()
            bindingView?.fullname?.isFocusable = true
            return false
        } else {
            registerRequest.fullname = bindingView?.fullname?.getText().toString()
        }

        if (bindingView?.email?.getText().equals("")) {
            Alerter.create(activity)
                .setTitle("Failed register")
                .setText("Please check your email")
                .show()
            bindingView?.email?.isFocusable = true
            return false
        } else {
            registerRequest.email = bindingView?.email?.getText().toString()
        }

        if (bindingView?.phoneNumber?.getText().equals("")) {
            Alerter.create(activity)
                .setTitle("Failed register")
                .setText("Please check your phone")
                .show()
            bindingView?.phoneNumber?.isFocusable = true
            return false
        } else {
            registerRequest.phone = bindingView?.phoneNumber?.getText().toString()
        }

        if (bindingView?.password?.getText().equals("")) {
            Alerter.create(activity)
                .setTitle("Failed register")
                .setText("Please check your password")
                .show()
            bindingView?.password?.isFocusable = true
            return false
        } else {
            registerRequest.password = bindingView?.password?.getText().toString()
        }

        if (bindingView?.switcher?.isChecked == false) {
            Alerter.create(activity)
                .setTitle("Failed register")
                .setText("Please check term condition")
                .show()
            return false
        }
        
        return true
    }

}