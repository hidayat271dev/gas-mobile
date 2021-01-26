package com.joker.lpgo.ui.address.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.joker.lpgo.data.mock.AddressListMock
import com.joker.lpgo.databinding.ScreenAddressBinding
import com.joker.lpgo.ui.address.adapter.AddressAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressListFragment : Fragment() {

    private var bindingView: ScreenAddressBinding? = null
    private lateinit var adapterAddress: AddressAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenAddressBinding.inflate(inflater, container, false)
        val view = bindingView?.root
        if (view != null) {
            setupView()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingView = null
    }

    fun setupView() {
        adapterAddress = AddressAdapter(arrayListOf())

        context?.let {
            bindingView?.recyclerView2?.layoutManager = LinearLayoutManager(it)
            bindingView?.recyclerView2?.adapter = adapterAddress
        }

        adapterAddress.addData(AddressListMock().data)
    }

}
