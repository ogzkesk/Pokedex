package com.ogzkesk.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.core.di.IODispatcher
import com.ogzkesk.core.di.MainDispatcher
import com.ogzkesk.core.model.PokemonsModel
import com.ogzkesk.core.util.Constants.RANDOM_ERROR
import com.ogzkesk.core.util.Resource
import com.ogzkesk.domain.usecase.home.FetchPokemonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchPokemons: FetchPokemonsUseCase,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : ViewModel() {


    private val _event = MutableSharedFlow<HomeEvent>()
    val event: SharedFlow<HomeEvent> get() = _event

    private val _pokemons = MutableStateFlow(PokemonsModel.initial())
    val pokemons: StateFlow<PokemonsModel> get() = _pokemons

    fun onSearch(query: String) {

    }

    private suspend fun emitEvents(resource: Resource<PokemonsModel>) {
        withContext(mainDispatcher) {
            when (resource) {
                is Resource.Loading -> {
                    _event.emit(HomeEvent.Loading)
                }

                is Resource.Error -> {
                    postError(resource.exception?.message ?: RANDOM_ERROR)
                }

                is Resource.Success -> {
                    _pokemons.update { resource.data }
                    _event.emit(HomeEvent.Success)
                }
            }
        }
    }

    private suspend fun postError(message: String) {
        withContext(mainDispatcher) {
            _event.emit(HomeEvent.Error(message))
        }
    }

    fun onSortTypeChanged(sortType: SortDialog.SortType) {
        when (sortType) {
            SortDialog.SortType.NUMBER -> {
                _pokemons.update { model ->
                    model.copy(results = pokemons.value.results.sortedBy { it.no })
                }
            }

            SortDialog.SortType.NAME -> {
                _pokemons.update { model ->
                    model.copy(results = pokemons.value.results.sortedBy { it.name })
                }
            }
        }
    }


    init {
        viewModelScope.launch(ioDispatcher) {
            fetchPokemons().collect {
                emitEvents(it)
            }
        }
    }
}