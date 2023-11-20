package com.ogzkesk.data.repository.pref

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ogzkesk.core.base.di.IODispatcher
import com.ogzkesk.core.base.ext.dataStore
import com.ogzkesk.core.base.util.Constants.DataStore.THEME_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DatastoreRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : DataRepository {

    private object PreferencesKeys {
        val themeKey = stringPreferencesKey(THEME_KEY)
    }

    private val dataStore = context.dataStore



}