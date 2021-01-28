package com.joker.lpgo.mobile.module.walktrough

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseActivity
import com.joker.lpgo.mobile.data.model.Walktrough
import com.joker.lpgo.mobile.data.preference.AppPreference
import com.joker.lpgo.mobile.databinding.ScreenWalkhtroughBinding
import com.joker.lpgo.mobile.module.auth.AuthView

class WalktroughView : BaseActivity() {

    private lateinit var bindingView: ScreenWalkhtroughBinding

    private var currentViewPagerIndex = 0
    private var dataWalkthrough = mutableListOf<Walktrough>(
        Walktrough(
            R.mipmap.illustrator01, "Title Walktrough", "Lorem ipsum dolor sit amet, pro tollit feugait constituam inLorem ipsum Lorem ipsum dolor sit amet, pro tollit feugait constituam inLorem ipsum "
        ),
        Walktrough(
            R.mipmap.illustrator02, "Title Walktrough", "Lorem ipsum dolor sit amet, pro tollit feugait constituam inLorem ipsum Lorem ipsum dolor sit amet, pro tollit feugait constituam inLorem ipsum "
        ),
        Walktrough(
            R.mipmap.illustrator03, "Title Walktrough", "Lorem ipsum dolor sit amet, pro tollit feugait constituam inLorem ipsum Lorem ipsum dolor sit amet, pro tollit feugait constituam inLorem ipsum "
        ),
    )

    private val walkthroughViewPagerListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            currentViewPagerIndex = position
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView = ScreenWalkhtroughBinding.inflate(layoutInflater)
        val view = bindingView.root
        setContentView(view)

        setupView()
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingView.viewPager2.unregisterOnPageChangeCallback(walkthroughViewPagerListener)
    }

    private fun setupView() {
        val walktroughAdapter = WalktroughPageAdapter(dataWalkthrough)
        bindingView.viewPager2.adapter = walktroughAdapter
        bindingView.wormDotsIndicator.setViewPager2(bindingView.viewPager2)

        bindingView.viewPager2.registerOnPageChangeCallback(walkthroughViewPagerListener)

        bindingView.button.setOnClickListener {
            if (currentViewPagerIndex < dataWalkthrough.size - 1) {
                currentViewPagerIndex++
                bindingView.viewPager2.currentItem = currentViewPagerIndex
            } else {
                AuthView.launchIntent(applicationContext)
                AppPreference.setAppFirstInstall(false)
                finish()
            }
        }
    }

    companion object {

        var TAG = "WalktroughView"

        fun launchIntent(context: Context) {
            val intent = Intent(context, WalktroughView::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

}