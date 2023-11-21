package com.ogzkesk.data.repository.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ogzkesk.core.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val datastore: DataStore<Preferences>,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : PreferencesRepository {

    private object PreferencesKeys {

    }


}