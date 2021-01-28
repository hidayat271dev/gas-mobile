package com.joker.lpgo.mobile.module.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.joker.lpgo.mobile.base.BaseActivity
import com.joker.lpgo.mobile.data.preference.AppPreference
import com.joker.lpgo.mobile.databinding.ScreenSplashBinding
import com.joker.lpgo.mobile.module.auth.AuthView
import com.joker.lpgo.mobile.module.home.HomeView
import com.joker.lpgo.mobile.module.walktrough.WalktroughView

class SplashView : BaseActivity() {

    private lateinit var bindingView: ScreenSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ScreenSplashBinding.inflate(layoutInflater)
        val view = bindingView.root
        setContentView(view)

        actionSplash()
    }

    fun actionSplash() {
        Handler().postDelayed({

            Log.e(TAG, "actionSplash: ")
            Log.e(TAG, "getAppFirstInstall " + AppPreference.getAppFirstInstall().toString())
            Log.e(TAG, "getAppIsLogin " + AppPreference.getAppIsLogin().toString())
            val isFirstInstall = AppPreference.getAppFirstInstall()
            val isLogin = AppPreference.getAppIsLogin()

            if (isFirstInstall) {
                WalktroughView.launchIntent(getActivityContext())
            } else {
                if (isLogin) {
                    HomeView.launchIntent(getActivityContext())
                } else {
                    AuthView.launchIntent(getActivityContext())
                }
            }

            finish()
        }, 2000)
    }

    companion object {

        var TAG = "SplashView"

        fun launchIntent(context: Context) {
            val intent = Intent(context, AuthView::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}