package com.joker.lpgo.base

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import com.github.heyalex.bottomdrawer.BottomDrawerFragment
import com.joker.lpgo.utils.extension.getSoftInputMode

open class AppBottomDialog : BottomDrawerFragment() {

    protected var originalMode : Int? = null

    override fun onDestroyView() {
        super.onDestroyView()
        originalMode?.let { dialog?.window?.setSoftInputMode(it) }
    }

    @Suppress("DEPRECATION")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )

        return dialog
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        originalMode = activity?.window?.getSoftInputMode()
        dialog?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }

}