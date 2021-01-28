package com.joker.lpgo.mobile.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.joker.lpgo.mobile.util.component.ProgressDialog
import com.orhanobut.hawk.Hawk

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPreference()
    }

    fun getActivityContext(): Context {
        return this
    }

    fun initPreference() {
        Hawk.init(applicationContext).build()
    }

    /* ========================== PROGRESS DIALOG ========================== */
    var progressDialog: ProgressDialog? = null
    fun isShowProgressDialog(isShow: Boolean) {
        if(isShow) {
            if (progressDialog != null) {
                progressDialog?.dismissAllowingStateLoss()
            }
            progressDialog = ProgressDialog.newInstance()
            progressDialog?.let { supportFragmentManager.beginTransaction().add(it, ProgressDialog.TAG).commit() }
        }
        else {
            progressDialog?.dismissAllowingStateLoss()
        }
    }

    /* ========================== PROGRESS DIALOG ========================== */

}