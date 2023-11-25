package com.ogzkesk.common.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ogzkesk.common.util.Constants.DataStore.DATA_STORE

fun Context.toAction(uri: Uri) {
    startActivity(Intent(Intent.ACTION_VIEW, uri))
}

fun Context.showToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showSnackbar(
    message: String?,
    duration: Int = Snackbar.LENGTH_LONG,
    @StringRes actionText: Int? = null,
    onAction: (View.OnClickListener)? = null
) {
    if (onAction != null && actionText != null) {
        Snackbar.make(requireView(), message ?: "", Snackbar.LENGTH_INDEFINITE)
            .setAction(actionText,onAction)
            .show()

        return
    }

    Snackbar.make(requireView(), message ?: "", duration).show()
}

fun Fragment.showSnackbar(
    @StringRes message: Int,
    duration: Int = Snackbar.LENGTH_LONG,
    @StringRes actionText: Int? = null,
    onAction: (View.OnClickListener)? = null
) {
    if (onAction != null && actionText != null) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
            .setAction(actionText,onAction)
            .show()

        return
    }

    Snackbar.make(requireView(), message, duration).show()
}

fun String.capitalize(): String {
    return replaceFirstChar { it.uppercase() }
}


