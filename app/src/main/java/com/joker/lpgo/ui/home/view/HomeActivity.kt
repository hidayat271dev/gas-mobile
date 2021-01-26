package com.joker.lpgo.ui.home.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.joker.lpgo.R
import com.joker.lpgo.base.AppBaseActivity
import com.joker.lpgo.databinding.ScreenHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppBaseActivity() {

    private lateinit var bindingView: ScreenHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ScreenHomeBinding.inflate(layoutInflater)
        val view = bindingView.root
        setContentView(view)
        setupView()
    }

    fun setupView() {

        bindingView.badge.setNumber(21, true)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bindingView.imageView15.setOnClickListener {
            navController.navigate(R.id.dashboardFragment)
        }

        bindingView.imageView16.setOnClickListener {
            navController.navigate(R.id.cartFragment)
        }

        bindingView.imageView17.setOnClickListener {
            navController.navigate(R.id.orderListFragment)
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
