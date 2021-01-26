package com.joker.lpgo.ui.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.joker.lpgo.R
import com.joker.lpgo.data.sharepreference.AppSharedPreference
import com.joker.lpgo.databinding.ScreenCurrrentLocationBinding
import com.joker.lpgo.databinding.ScreenDashboardBinding
import com.joker.lpgo.databinding.ScreenProfileBinding
import com.joker.lpgo.ui.auth.view.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var bindingView: ScreenProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenProfileBinding.inflate(inflater, container, false)
        val view = bindingView?.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingView = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingView?.textView12?.setOnClickListener {
            context?.let {
                AuthActivity.launchIntent(it)
                AppSharedPreference.instance.isAppLogin(false)
                activity?.finish()
            }
        }

        bindingView?.menuAbout?.containerMenu?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_aboutFragment)
        }

        bindingView?.menuAddress?.containerMenu?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_addressListFragment)
        }

        bindingView?.menuMyOrder?.containerMenu?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_orderListFragment)
        }
    }

}
