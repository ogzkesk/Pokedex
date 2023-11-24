package com.ogzkesk.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ogzkesk.core.model.ResultModel
import com.ogzkesk.domain.usecase.home.FetchPokemonsUseCase
import com.ogzkesk.home.content.SortType
import com.ogzkesk.home.data.HomePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchPokemons: FetchPokemonsUseCase,
) : ViewModel() {

    private val transformer = MutableStateFlow(Pair(SortType.NAME, ""))

    private val _pagingFlow = MutableStateFlow<PagingData<ResultModel>>(PagingData.empty())
    val pagingFlow: StateFlow<PagingData<ResultModel>> get() = _pagingFlow

    fun onSearch(query: String) {
        transformer.update { it.copy(second = query) }
    }


    fun onSortTypeChanged(type: SortType) {
        transformer.update { it.copy(first = type) }
    }

    init {

        viewModelScope.launch {

            transformer.collectLatest { pair ->
                val (type, query) = pair

                val pager = Pager(
                    config = PagingConfig(
                        pageSize = 20,
                        enablePlaceholders = true,
                        prefetchDistance = 5
                    ),
                    pagingSourceFactory = {
                        HomePagingSource(fetchPokemons, query, type)
                    }
                )

                pager.flow.cachedIn(viewModelScope).collect {
                    _pagingFlow.value = it
                }
            }
        }
    }
}