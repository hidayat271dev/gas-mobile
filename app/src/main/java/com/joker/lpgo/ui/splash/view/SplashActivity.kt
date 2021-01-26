package com.joker.lpgo.ui.splash.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.joker.lpgo.BuildConfig
import com.joker.lpgo.base.AppBaseActivity
import com.joker.lpgo.data.sharepreference.AppSharedPreference
import com.joker.lpgo.databinding.ScreenSplashBinding
import com.joker.lpgo.ui.auth.view.AuthActivity
import com.joker.lpgo.ui.home.view.HomeActivity
import com.joker.lpgo.ui.walktrough.view.WalktroughActivity
import dagger.hilt.android.AndroidEntryPoint
import io.easyprefs.Prefs

@AndroidEntryPoint
class SplashActivity : AppBaseActivity() {

    private lateinit var bindingView: ScreenSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ScreenSplashBinding.inflate(layoutInflater)
        val view = bindingView.root
        setContentView(view)

        setupView()
    }

    private fun setupView() {
        bindingView.textVersionApp.text = "App version ".plus(BuildConfig.VERSION_NAME)

        val isFirstInstall = AppSharedPreference.instance.isAppFirstInstall()
        val isLogin = AppSharedPreference.instance.isAppLogin()

        Log.e(TAG, "setupView: ")
        Log.e(TAG, "isFirstInstall: ".plus(isFirstInstall.toString()))
        Log.e(TAG, "isLogin: ".plus(isLogin.toString()))

        Handler().postDelayed({

            if (isFirstInstall == true) {
                WalktroughActivity.launchIntent(applicationContext)
            } else {
                if (isLogin == true) {
                    HomeActivity.launchIntent(applicationContext)
                } else {
                    AuthActivity.launchIntent(applicationContext)
                }
            }
            finish()
        }, 2000)
    }

    companion object {

        var TAG = "SplashActivity"

    }

}
