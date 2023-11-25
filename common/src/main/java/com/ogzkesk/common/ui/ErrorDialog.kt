package com.ogzkesk.common.ui

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ogzkesk.core.R

class ErrorDialog(private val context: Context) {

    fun show(message: String,onConfirm: () -> Unit){
        val dialog = MaterialAlertDialogBuilder(context)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { dialogInterface, _ ->
                onConfirm()
                dialogInterface.dismiss()
            }

        dialog.show()
    }
}