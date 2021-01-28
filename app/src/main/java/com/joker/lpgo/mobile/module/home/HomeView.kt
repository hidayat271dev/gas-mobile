package com.joker.lpgo.mobile.module.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseActivity
import com.joker.lpgo.mobile.databinding.ScreenAuthBinding
import com.joker.lpgo.mobile.databinding.ScreenHomeBinding
import com.joker.lpgo.mobile.module.auth.AuthView

class HomeView : BaseActivity() {

    lateinit var bindingView: ScreenHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ScreenHomeBinding.inflate(layoutInflater)
        val view = bindingView.root
        setContentView(view)
        setupView()
    }

    fun setupView() {
        val navBuilder = NavOptions.Builder()
        navBuilder
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_right)
            .setPopExitAnim(R.anim.slide_out_right)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment2) as NavHostFragment
        val navController = navHostFragment.navController

        bindingView.imageView15.setOnClickListener {
            navController.navigate(R.id.dashboardView, null, navBuilder.build())
        }

        bindingView.imageView16.setOnClickListener {
            navController.navigate(R.id.cartView, null, navBuilder.build())
        }

        bindingView.imageView17.setOnClickListener {
            navController.navigate(R.id.orderListView, null, navBuilder.build())
        }

    }



    companion object {

        var TAG = "HomeView"

        fun launchIntent(context: Context) {
            val intent = Intent(context, HomeView::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}