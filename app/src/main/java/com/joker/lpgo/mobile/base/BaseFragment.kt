package com.joker.lpgo.mobile.base

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.joker.lpgo.mobile.R

abstract class BaseFragment: Fragment() {

    fun getBaseActivity(): BaseActivity? {
        var context = context
        while (context is ContextWrapper) {
            if (context is BaseActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

    fun getActivityContext(): Context? {
        return getBaseActivity()
    }

    fun navigatePage(page: Int, view: View, data: Bundle? = null) {
        val navBuilder = NavOptions.Builder()
        navBuilder
            .setEnterAnim(R.anim.slide_in_top)
            .setExitAnim(R.anim.slide_out_top)
            .setPopEnterAnim(R.anim.slide_in_top)
            .setPopExitAnim(R.anim.slide_out_top)

        Navigation.findNavController(view).navigate(page, data, navBuilder.build())
    }

    fun navigatePageLeft(page: Int, view: View, data: Bundle? = null) {
        val navBuilder = NavOptions.Builder()
        navBuilder
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_right)
            .setPopExitAnim(R.anim.slide_out_right)

        Navigation.findNavController(view).navigate(page, data, navBuilder.build())
    }
}