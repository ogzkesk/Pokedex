package com.ogzkesk.core.ui

import android.content.Context

class CustomDialog(context: Context) {

    private val progressDialog = ProgressDialog(context)
    private val errorDialog by lazy { ErrorDialog(context) }

    fun showProgress(){
        progressDialog.show()
    }

    fun showError(message: String,onConfirm: () -> Unit = {}){
        errorDialog.show(message,onConfirm)
    }

    fun dismiss(){
        progressDialog.dismiss()
    }
}