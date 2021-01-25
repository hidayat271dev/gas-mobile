package com.joker.lpgo.ui.auth.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joker.lpgo.databinding.ScreenAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var bindingView: ScreenAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ScreenAuthBinding.inflate(layoutInflater)
        val view = bindingView.root
        setContentView(view)
        setupView()
    }

    fun setupView() {

    }

    companion object {

        var TAG = "AuthActivity"

        fun launchIntent(context: Context) {
            val intent = Intent(context, AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}