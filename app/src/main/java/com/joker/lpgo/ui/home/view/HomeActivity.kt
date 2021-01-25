package com.joker.lpgo.ui.home.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.joker.lpgo.R
import com.joker.lpgo.databinding.ScreenHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var bindingView: ScreenHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ScreenHomeBinding.inflate(layoutInflater)
        val view = bindingView.root
        setContentView(view)
        setupView()
    }

    fun setupView() {

    }


    companion object {

        var TAG = "HomeActivity"

        fun launchIntent(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}
