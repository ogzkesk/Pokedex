package com.ogzkesk.home

import androidx.lifecycle.ViewModel
import com.ogzkesk.core.base.di.IODispatcher
import com.ogzkesk.domain.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


}