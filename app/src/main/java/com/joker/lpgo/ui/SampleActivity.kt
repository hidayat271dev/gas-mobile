package com.joker.lpgo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joker.lpgo.databinding.ActivityMainBinding
import com.joker.lpgo.databinding.ScreenAuthBinding
import com.joker.lpgo.databinding.ScreenRegisterBinding
import com.joker.lpgo.databinding.ScreenWalkhtroughBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity : AppCompatActivity() {

    private lateinit var bindingView: ScreenRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ScreenRegisterBinding.inflate(layoutInflater)
        val view = bindingView.root
        setContentView(view)
    }

}