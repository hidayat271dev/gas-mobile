package com.joker.lpgo.mobile.data.preference

import android.util.Log
import com.joker.lpgo.mobile.data.model.Cart
import com.joker.lpgo.mobile.data.model.User
import com.orhanobut.hawk.Hawk

object AppPreference {

    val IS_APP_FIRST = "IS_APP_FIRST"
    val IS_APP_LOGIN = "IS_APP_LOGIN"
    val APP_TOKEN = "APP_TOKEN"
    val CURRENT_USER = "CURRENT_USER"
    val CART_USER = "CART_USER"

    fun setAppFirstInstall(isFirstInstall: Boolean?) {
        Hawk.put(IS_APP_FIRST, isFirstInstall)
    }

    fun getAppFirstInstall(): Boolean {
        if (Hawk.get<Boolean>(IS_APP_FIRST) != null) {
            return Hawk.get<Boolean>(IS_APP_FIRST)
        }
        return true
    }

    fun setAppIsLogin(isLogin: Boolean?) {
        Hawk.put(IS_APP_LOGIN, isLogin)
    }

    fun getAppIsLogin(): Boolean {
        if (Hawk.get<Boolean>(IS_APP_LOGIN) != null) {
            return Hawk.get<Boolean>(IS_APP_LOGIN)
        }
        return false
    }

    fun setAppToken(token: String?) {
        Hawk.put(APP_TOKEN, token)
    }

    fun getAppToken(): String {
        if (Hawk.get<String>(APP_TOKEN) != null) {
            return Hawk.get<String>(APP_TOKEN)
        }
        return ""
    }

    fun setCurrentUser(user: User?) {
        Hawk.put(CURRENT_USER, user)
    }

    fun getCurrentUser(): User? {
        if (Hawk.get<User>(CURRENT_USER) != null) {
            return Hawk.get<User>(CURRENT_USER)
        }
        return null
    }

    fun setCartUser(cart: Cart?) {
        var data = getCartUser()
        if (data.isNullOrEmpty()) {
            Hawk.put(CART_USER, listOf(cart))
        } else {
            var temp = mutableListOf<Cart>()
            data.forEachIndexed { index, d ->
                if (d.uuid == cart?.uuid) {
                    temp.add(cart)
                } else {
                    temp.add(d)
                }
            }
            var isExist = data.find { it.uuid == cart?.uuid }
            if (isExist == null) {
                cart?.let { temp.add(it) }
            }
            Hawk.put(CART_USER, temp)
        }
    }

    fun setRemoveCart(cart: Cart?) {
        var data = getCartUser()
        var temp = mutableListOf<Cart>()
        data?.let {
            temp.addAll(it)
            temp.forEachIndexed { index, d ->
                if (d.uuid == cart?.uuid) {
                    temp.removeAt(index)
                }
            }
            Hawk.put(CART_USER, temp)
        }
    }

    fun setRemoveAllCart() {
        var temp = mutableListOf<Cart>()
        Hawk.put(CART_USER, temp)
    }

    fun getCartUser(): List<Cart>? {
        if (Hawk.get<List<Cart>>(CART_USER) != null) {
            return Hawk.get<List<Cart>>(CART_USER)
        }
        return null
    }

}