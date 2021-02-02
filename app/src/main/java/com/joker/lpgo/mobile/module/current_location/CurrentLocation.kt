package com.joker.lpgo.mobile.module.current_location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.data.preference.AppPreference
import com.joker.lpgo.mobile.databinding.ScreenCurrrentLocationBinding
import com.joker.lpgo.mobile.module.address_add.AddressAddView
import com.joker.lpgo.mobile.module.product_detail.ProductDetailView
import com.joker.lpgo.mobile.util.extension.getAddressInfo

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

            val address = AppPreference.getCurrentAddress()
            if (address == null) {
                val primaryLocation = LatLng(-6.8828687, 107.5836387)
                mMap.addMarker(MarkerOptions().position(primaryLocation).title("Your Primary Location"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(primaryLocation, 15f))
            } else {
                val primaryLocation = LatLng(address.lat, address.long)
                mMap.addMarker(MarkerOptions().position(primaryLocation).title("Your Primary Location"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(primaryLocation, 15f))
            }


            mMap.setOnMapLongClickListener {
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(it))
                AddressAddView.newInstance(it.latitude, it.longitude).let {
                    childFragmentManager.beginTransaction().add(it, it.TAG).commit()
                }
            }
        }
    }
}