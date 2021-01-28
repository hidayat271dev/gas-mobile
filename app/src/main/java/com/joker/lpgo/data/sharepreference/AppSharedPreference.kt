package com.joker.lpgo.data.sharepreference

import io.easyprefs.Prefs

class AppSharedPreference {

    val IS_APP_FIRST = "IS_APP_FIRST"
    val IS_APP_LOGIN = "IS_APP_LOGIN"
    val APP_TOKEN = "APP_TOKEN"

    fun isAppFirstInstall(isAppFirstInstall: Boolean? = null) : Boolean? {
        if (isAppFirstInstall != null) {
            Prefs.securely().write().content(IS_APP_FIRST, isAppFirstInstall).commit()
        }

        return Prefs.securely().read().content(IS_APP_FIRST,  false)
    }

    fun isAppLogin(isAppLogin: Boolean? = null) : Boolean {
        if (isAppLogin != null) {
            Prefs.securely().write().content(IS_APP_LOGIN, isAppLogin).commit()
        }

        return Prefs.securely().read().content(IS_APP_LOGIN,  false)
    }

    fun appToken(token: String? = null) : String {
        if (token != null) {
            Prefs.securely().write().content(IS_APP_LOGIN, token).commit()
        }

        return Prefs.securely().read().content(APP_TOKEN, "")
    }

    companion object {
        val instance = AppSharedPreference()
    }
}
