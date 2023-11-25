package com.ogzkesk.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ogzkesk.common.model.PokemonModel
import com.ogzkesk.common.util.Constants.RANDOM_ERROR
import com.ogzkesk.common.util.Resource
import com.ogzkesk.domain.usecase.detail.FetchPokemonByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val NO_NAME_ERROR = "Something went wrong go back"

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val fetchPokemonByNameUseCase: FetchPokemonByNameUseCase,
) : ViewModel() {


    private val _event = Channel<DetailsEvent>()
    val event get() = _event.receiveAsFlow()

    private val _pokemon = MutableStateFlow<PokemonModel?>(null)
    val pokemon: StateFlow<PokemonModel?> get() = _pokemon

    fun getPokemon(name: String?) {
        viewModelScope.launch {

            if (name == null) {
                _event.send(DetailsEvent.Error(NO_NAME_ERROR))
                return@launch
            }

            fetchPokemonByNameUseCase(name).collect {
                emitEvents(it)
            }
        }
    }

    private suspend fun emitEvents(resource: Resource<PokemonModel>) {
        when (resource) {
            Resource.Loading -> {
                _event.send(DetailsEvent.Loading)
            }

            is Resource.Error -> {
                _event.send(DetailsEvent.Error(resource.exception?.message ?: RANDOM_ERROR))
            }

            is Resource.Success -> {
                _pokemon.update { resource.data }
                _event.send(DetailsEvent.Success)
            }
        }
    }
}