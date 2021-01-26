package com.joker.lpgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.easyprefs.Prefs

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Prefs.initializeApp(this)
    }
}
