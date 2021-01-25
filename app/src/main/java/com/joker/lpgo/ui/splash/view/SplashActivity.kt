package com.joker.lpgo.ui.splash.view

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.joker.lpgo.BuildConfig
import com.joker.lpgo.databinding.ScreenSplashBinding
import com.joker.lpgo.ui.walktrough.view.WalktroughActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

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

        Handler().postDelayed({
            WalktroughActivity.launchIntent(applicationContext)
            finish()
        }, 2000)
    }

}
