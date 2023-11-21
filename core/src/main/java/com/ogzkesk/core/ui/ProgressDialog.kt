package com.ogzkesk.core.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.progressindicator.CircularProgressIndicator

internal class ProgressDialog(context: Context): Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val progressBar = CircularProgressIndicator(context).apply {
            isIndeterminate = true
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setIndicatorColor(Color.WHITE)
        }

        val container = FrameLayout(context).apply {
            setBackgroundColor(Color.TRANSPARENT)
            addView(progressBar)
        }

        setContentView(container)
        setCancelable(false)
        setCanceledOnTouchOutside(false)

        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}
