package com.joker.lpgo.mobile.module.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.joker.lpgo.mobile.base.BaseActivity
import com.joker.lpgo.mobile.databinding.ScreenAuthBinding

class AuthView : BaseActivity() {

    private lateinit var bindingView: ScreenAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ScreenAuthBinding.inflate(layoutInflater)
        val view = bindingView.root
        setContentView(view)
    }

    companion object {

        var TAG = "AuthView"

        fun launchIntent(context: Context) {
            val intent = Intent(context, AuthView::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}