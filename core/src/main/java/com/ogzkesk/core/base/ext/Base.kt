package com.ogzkesk.core.base.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.app.ShareCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ogzkesk.core.R
import com.ogzkesk.core.base.util.Constants.DataStore.DATA_STORE
import com.ogzkesk.core.base.util.Constants.SHARE_TYPE

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


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE)


fun Context.share(text: String) {
    ShareCompat.IntentBuilder(this)
        .setType(SHARE_TYPE)
        .setText(text)
        .setChooserTitle(R.string.share_with)
        .startChooser()
}
