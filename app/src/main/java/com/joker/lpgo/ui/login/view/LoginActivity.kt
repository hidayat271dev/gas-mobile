package com.joker.lpgo.ui.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joker.lpgo.R
import com.joker.lpgo.databinding.ScreenAuthBinding
import com.joker.lpgo.ui.home.view.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var bindingView: ScreenAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ScreenAuthBinding.inflate(layoutInflater)
        val view = bindingView.root
        setContentView(view)
        setupView()
    }

    private fun setupView() {
        bindingView.htmlText.setHtml(getString(R.string.register_desc))

        bindingView.button2.setOnClickListener {
            HomeActivity.launchIntent(applicationContext)
            finish()
        }
    }


    companion object {

        var TAG = "LoginActivity"

        fun launchIntent(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}
