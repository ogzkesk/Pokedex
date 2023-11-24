package com.ogzkesk.home.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ogzkesk.core.model.ResultModel
import com.ogzkesk.domain.usecase.home.FetchPokemonsUseCase
import com.ogzkesk.home.content.SortType
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
private const val CONNECTION_ERROR = "Please check your internet connection"

class HomePagingSource @Inject constructor(
    private val fetchPokemons: FetchPokemonsUseCase,
    private val query: String,
    private val sortType: SortType
) : PagingSource<Int, ResultModel>() {

    override fun getRefreshKey(state: PagingState<Int, ResultModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultModel> {
        return try {
            val position = params.key ?: 0
            val pageSize = params.loadSize

            val items = fetchPokemons(position, pageSize).results
                .sortedBy { if(sortType == SortType.NAME) it.name else it.no }
                .filter { it.name.contains(other = query,ignoreCase = true) }

            LoadResult.Page(
                data = items,
                prevKey = if (position > 0) position - pageSize else null,
                nextKey = if (items.size >= pageSize) position + pageSize else null
            )
        } catch (e: IOException) {
            LoadResult.Error(Throwable(CONNECTION_ERROR))
        } catch (e: HttpException) {
            LoadResult.Error(Throwable(e.message()))
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}