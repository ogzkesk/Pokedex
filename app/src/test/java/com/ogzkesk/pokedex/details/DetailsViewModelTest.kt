package com.ogzkesk.pokedex.details

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ogzkesk.common.model.PokemonModel
import com.ogzkesk.common.util.Resource
import com.ogzkesk.data.repository.PokedexRepository
import com.ogzkesk.details.DetailsEvent
import com.ogzkesk.details.DetailsViewModel
import com.ogzkesk.domain.usecase.detail.FetchPokemonByNameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {


    private val testDispatcher = UnconfinedTestDispatcher()
    private val repository : PokedexRepository = mock()
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailsViewModel(FetchPokemonByNameUseCase(repository))
    }


    @Test
    fun `event emits loading then error`() = runTest(testDispatcher){
        val errorFlow = flow<Resource<PokemonModel>> {
            emit(Resource.Loading)
            emit(Resource.Error())
        }

        `when`(repository.fetchPokemonByName("")).thenReturn(errorFlow)

        viewModel.getPokemon("")
        viewModel.event.test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(DetailsEvent.Loading::class.java)
            advanceTimeBy(1000)
            val success = awaitItem()
            assertThat(success).isInstanceOf(DetailsEvent.Error::class.java)
            ensureAllEventsConsumed()

        }
    }

    @Test
    fun `event emits loading then success`() = runTest(testDispatcher){

        val pokemonModel : PokemonModel = mock()
        val successFlow = flow{
            emit(Resource.Loading)
            emit(Resource.Success(pokemonModel))
        }

        `when`(repository.fetchPokemonByName("")).thenReturn(successFlow)

        viewModel.getPokemon("")
        viewModel.event.test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(DetailsEvent.Loading::class.java)
            advanceTimeBy(1000)
            val success = awaitItem()
            assertThat(success).isInstanceOf(DetailsEvent.Success::class.java)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when event emits success pokemon data should be not null`() = runTest(testDispatcher){

        val pokemonModel : PokemonModel = mock()
        val successFlow = flow{
            emit(Resource.Loading)
            emit(Resource.Success(pokemonModel))
        }

        `when`(repository.fetchPokemonByName("")).thenReturn(successFlow)

        viewModel.getPokemon("")
        viewModel.event.test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(DetailsEvent.Loading::class.java)
            advanceTimeBy(1000)
            val success = awaitItem()
            assertThat(success).isInstanceOf(DetailsEvent.Success::class.java)
            ensureAllEventsConsumed()
        }

        viewModel.pokemon.test {
            val data = awaitItem()
            assertThat(data).isEqualTo(pokemonModel)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when event emits error pokemon data should be null`() = runTest(testDispatcher){

        val errorFlow = flow<Resource<PokemonModel>> {
            emit(Resource.Loading)
            emit(Resource.Error())
        }

        `when`(repository.fetchPokemonByName("")).thenReturn(errorFlow)

        viewModel.getPokemon("")
        viewModel.event.test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(DetailsEvent.Loading::class.java)
            advanceTimeBy(1000)
            val success = awaitItem()
            assertThat(success).isInstanceOf(DetailsEvent.Error::class.java)
            ensureAllEventsConsumed()
        }

        viewModel.pokemon.test {
            val data = awaitItem()
            assertThat(data).isEqualTo(null)
            ensureAllEventsConsumed()
        }
    }


    @After
    fun release(){
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

}