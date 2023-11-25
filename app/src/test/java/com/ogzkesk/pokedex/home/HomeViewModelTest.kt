package com.ogzkesk.pokedex.home

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ogzkesk.data.repository.PokedexRepository
import com.ogzkesk.domain.usecase.home.FetchPokemonsUseCase
import com.ogzkesk.home.HomeViewModel
import com.ogzkesk.home.content.SortType
import com.ogzkesk.home.data.HomePagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: PokedexRepository
    private lateinit var fetchPokemonsUseCase: FetchPokemonsUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = FakePokedexRepository()
        fetchPokemonsUseCase = FetchPokemonsUseCase(repository)
        viewModel = HomeViewModel(fetchPokemonsUseCase)
    }


    @Test
    fun `pagingFlow should returns pagingData instance`() = runTest(testDispatcher) {
        viewModel.pagingFlow.test {
            val data = awaitItem()
            assertThat((data)).isInstanceOf(PagingData::class.java)
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `pagingFlow should return given data in fakeRepository`() = runTest(testDispatcher) {
        val expectedData = HomeTestUtils.fakePokemonsData

        val result = viewModel.pagingFlow.asSnapshot()

        assertThat(result).containsExactlyElementsIn(expectedData)
    }

    @Test
    fun `pagingSource should return data ordered by sortType`() = runTest(testDispatcher) {

        val expectedData = HomeTestUtils.fakePokemonsData.sortedBy { it.name }

        val pager = TestPager(
            PagingConfig(20),
            HomePagingSource(fetchPokemonsUseCase,"",SortType.NAME)
        )

        val result = pager.refresh() as PagingSource.LoadResult.Page
        assertThat(result.data).isEqualTo(expectedData)
    }

    @Test
    fun `pagingSource should return pikachu on query`() = runTest(testDispatcher) {

        val query = "Pikachu"
        val pager = TestPager(
            PagingConfig(20),
            HomePagingSource(fetchPokemonsUseCase,query,SortType.NAME)
        )

        val result = pager.refresh() as PagingSource.LoadResult.Page
        assertThat(result.data.first().name).isEqualTo(query)
    }

    @Test
    fun `transformation should returns sortType number`() = runTest(testDispatcher) {

        viewModel.onSortTypeChanged(SortType.NAME)

        val tField = viewModel.javaClass.getDeclaredField("transformer")
        tField.isAccessible = true
        val transformation = tField.get(viewModel) as MutableStateFlow<*>

        println(transformation.value)
        transformation.test {
            val data = awaitItem()
            assertThat(data).isEqualTo(Pair(SortType.NAME, ""))
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `transformation should returns query pikachu`() = runTest(testDispatcher) {

        viewModel.onSearch("pikachu")

        val tField = viewModel.javaClass.getDeclaredField("transformer")
        tField.isAccessible = true
        val transformation = tField.get(viewModel) as MutableStateFlow<*>

        println(transformation.value)
        transformation.test {
            val data = awaitItem()
            assertThat(data).isEqualTo(Pair(SortType.NUMBER, "pikachu"))
            ensureAllEventsConsumed()
        }
    }


    @After
    fun release() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }
}