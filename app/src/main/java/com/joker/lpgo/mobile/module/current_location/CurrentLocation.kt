package com.joker.lpgo.mobile.module.current_location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.databinding.ScreenCurrrentLocationBinding

class CurrentLocation : BaseFragment(), OnMapReadyCallback {

    private var bindingView: ScreenCurrrentLocationBinding? = null

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenCurrrentLocationBinding.inflate(inflater, container, false)
        val view = bindingView?.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingView = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            mMap = it
        }
    }
}