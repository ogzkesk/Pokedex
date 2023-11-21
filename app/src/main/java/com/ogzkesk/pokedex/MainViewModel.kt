package com.ogzkesk.pokedex

import androidx.lifecycle.ViewModel
import com.ogzkesk.core.di.IODispatcher
import com.ogzkesk.domain.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    preferencesManager: PreferencesManager,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {



}