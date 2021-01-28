package com.joker.lpgo.mobile.module.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.data.model.User
import com.joker.lpgo.mobile.data.preference.AppPreference
import com.joker.lpgo.mobile.data.remote.request.ForgotRequest
import com.joker.lpgo.mobile.databinding.ScreenForgotBinding
import com.joker.lpgo.mobile.databinding.ScreenProfileBinding
import com.joker.lpgo.mobile.module.auth.AuthView

class ProfileView : BaseFragment() {

    private var bindingView: ScreenProfileBinding? = null
    private var currentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenProfileBinding.inflate(inflater, container, false)
        return bindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewListener()
    }

    fun setupView() {
        bindingView?.menuAbout?.textView13?.text = "About App"
        bindingView?.menuMyOrder?.textView13?.text = "My Order"
        bindingView?.menuAddress?.textView13?.text = "Address"

        currentUser = AppPreference.getCurrentUser()
        currentUser?.let {
            bindingView?.textView11?.text = it.fullname
            bindingView?.profileImage?.let { it1 ->
                Glide
                    .with(requireContext())
                    .load(it.pic_profile)
                    .centerCrop()
                    .placeholder(R.drawable.ic_no_image_default)
                    .error(R.drawable.ic_no_image_default)
                    .into(it1)
            }
        }
    }

    fun setupViewListener() {
        bindingView?.textView12?.setOnClickListener {
            AppPreference.setRemoveAllCart()
            AppPreference.setAppIsLogin(false)
            AppPreference.setCurrentUser(null)
            AuthView.launchIntent(requireContext())
            activity?.finish()
        }

        bindingView?.menuAbout?.containerMenu?.setOnClickListener {
            navigatePageLeft(R.id.aboutView, it)
        }

        bindingView?.menuMyOrder?.containerMenu?.setOnClickListener {
            navigatePageLeft(R.id.orderListView, it)
        }
    }
}