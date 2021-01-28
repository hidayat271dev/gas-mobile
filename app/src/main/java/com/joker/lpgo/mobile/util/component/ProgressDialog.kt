package com.joker.lpgo.mobile.util.component

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.joker.lpgo.mobile.R

class ProgressDialog : DialogFragment() {

    override fun getTheme(): Int {
        return R.style.AppFullDialogTheme
    }

    override fun onStart() {
        super.onStart()

        if (dialog == null) {
            return
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        var view = inflater.inflate(R.layout.component_progress_page, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        var params = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params

        dialog?.window?.setWindowAnimations(R.style.AnimationFade)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    companion object {
        const val TAG = "ProgressDialog"

        fun newInstance(): ProgressDialog {
            var fragment = ProgressDialog()
            fragment.apply {
                arguments = Bundle().apply {

                }
            }
            return fragment
        }
    }
}
