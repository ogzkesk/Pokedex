package com.ogzkesk.core.base.connection

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.ogzkesk.core.R

class NetworkDialog(private val context: Context) {

    fun show(onDialogClick: () -> Unit){
        val dialog = AlertDialog.Builder(context)
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