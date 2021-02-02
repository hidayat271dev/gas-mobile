package com.joker.lpgo.mobile.module.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.joker.lpgo.mobile.R
import com.joker.lpgo.mobile.base.BaseFragment
import com.joker.lpgo.mobile.data.model.Cart
import com.joker.lpgo.mobile.data.preference.AppPreference
import com.joker.lpgo.mobile.data.remote.AppApi
import com.joker.lpgo.mobile.data.remote.endpoint.AuthApi
import com.joker.lpgo.mobile.data.remote.endpoint.CheckoutApi
import com.joker.lpgo.mobile.databinding.ScreenCheckoutBinding
import com.joker.lpgo.mobile.module.home.HomeView
import com.joker.lpgo.mobile.util.extension.getAddressInfo
import com.tapadoo.alerter.Alerter
import io.reactivex.android.schedulers.AndroidSchedulers

class CheckoutView : BaseFragment() {

    private var bindingView: ScreenCheckoutBinding? = null
    private var dataCart: List<Cart> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView = ScreenCheckoutBinding.inflate(inflater, container, false)
        val view = bindingView?.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingView = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataCart()

        var subTotal = 0
        var delivery = 20000
        var items = mutableListOf<String>()
        var qtys = mutableListOf<Int>()

        dataCart?.forEach {
            subTotal += (it.price * it.qty)
            items.add(it.uuid)
            qtys.add(it.qty)
        }

        var tax = subTotal * (0.1)
        var total = subTotal + delivery + tax
        bindingView?.subTotal?.text = "Rp. ${subTotal}"
        bindingView?.delivery?.text = "Rp. ${delivery}"
        bindingView?.tax?.text = "Rp. ${tax}"
        bindingView?.total?.text = "Rp. ${total}"


        context?.let {
            val address = AppPreference.getCurrentAddress()
            if (address == null) {
                bindingView?.name?.text = "Home"
                bindingView?.text?.text = "".getAddressInfo(it, -6.8828687, 107.5836387)
            } else {
                bindingView?.name?.text = address.name
                bindingView?.text?.text = "".getAddressInfo(it, address.lat, address.long)
            }
        }


        bindingView?.button9?.setOnClickListener {

            getBaseActivity()?.isShowProgressDialog(true)

            AppApi
                .getRequest(context = context, withTokenAuth = true)
                ?.create(CheckoutApi::class.java)
                ?.postOrder(
                    items = items,
                    qyts = qtys,
                    status = 0,
                    total = total.toInt()
                )
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                    { result ->
                        getBaseActivity()?.isShowProgressDialog(false)
                        val data = result.body()
                        if(result.isSuccessful) {
                            AppPreference.setRemoveAllCart()
                            Alerter.create(activity)
                                .setTitle("Order success")
                                .setText("Please wait until operator confirm")
                                .show()
                            (activity as HomeView).addToCart()
                            Navigation.findNavController(view).popBackStack(R.id.dashboardView, true)
                        } else {
                            Alerter.create(activity)
                                .setTitle(data?.error?.title.toString())
                                .setText(data?.error?.message.toString())
                                .show()
                        }
                    },
                    { error ->
                        getBaseActivity()?.isShowProgressDialog(false)
                        Alerter.create(activity)
                            .setTitle("Failed checkout")
                            .setText("Server is busy")
                            .show()
                        error.printStackTrace()
                    }
                )


        }

    }

    fun getDataCart() {
        AppPreference.getCartUser()?.let {
            dataCart = it
        }
    }
}