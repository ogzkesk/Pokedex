package com.ogzkesk.core.ui

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ogzkesk.core.R

class NetworkDialog(private val context: Context) {

    fun show(onDialogClick: () -> Unit){
        val dialog = MaterialAlertDialogBuilder(context)
            .setIcon(R.drawable.ic_no_signal)
            .setTitle(R.string.no_connection)
            .setMessage(R.string.no_connection_desc)
            .setPositiveButton(R.string.ok) { dialogInterface, _ ->
                onDialogClick()
                dialogInterface.dismiss()
            }

        dialog.show()
    }
}