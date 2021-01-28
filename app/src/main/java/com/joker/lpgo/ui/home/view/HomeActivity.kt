package com.joker.lpgo.ui.home.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.joker.lpgo.R
import com.joker.lpgo.base.AppBaseActivity
import com.joker.lpgo.databinding.ScreenHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppBaseActivity() {

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
        navBuilder.setEnterAnim(android.R.anim.slide_in_left).setExitAnim(android.R.anim.slide_out_right).setPopEnterAnim(android.R.anim.slide_in_left).setPopExitAnim(android.R.anim.slide_out_right)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bindingView.imageView15.setOnClickListener {
            navController.navigate(R.id.dashboardFragment, null, navBuilder.build())
        }

        bindingView.imageView16.setOnClickListener {
            navController.navigate(R.id.cartFragment, null, navBuilder.build())
        }

        bindingView.imageView17.setOnClickListener {
            navController.navigate(R.id.orderListFragment, null, navBuilder.build())
        }

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
