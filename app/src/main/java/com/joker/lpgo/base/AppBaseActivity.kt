package com.joker.lpgo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.easyprefs.Prefs

open class AppBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Prefs.initializeApp(this)
    }

}