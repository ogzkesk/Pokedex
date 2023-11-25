package com.ogzkesk.common.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ogzkesk.common.base.Mapper
import com.ogzkesk.common.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private const val CONNECTION_ERROR = "Please check your internet connection"

fun <I, O> safeApiCall(
    dispatcher: CoroutineDispatcher,
    mapper: Mapper<I, O>,
    apiCall: suspend () -> I,
): Flow<Resource<O>> {
    return apiCall
        .asFlow()
        .flowOn(dispatcher)
        .asResource(mapper)
}

fun <I, O> Flow<I>.asResource(mapper: Mapper<I, O>): Flow<Resource<O>> {
    return map<I, Resource<O>> { Resource.Success(mapper(it)) }
        .onStart { emit(Resource.Loading) }
        .catch { e ->
            when (e) {
                is HttpException -> {
                    emit(Resource.Error(Throwable(e.message())))
                }

                is IOException -> {
                    emit(Resource.Error(Throwable(CONNECTION_ERROR)))
                }

                else -> {
                    emit(Resource.Error(e))
                }
            }
        }
}


fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T,
): Flow<Resource<T>> {
    return apiCall
        .asFlow()
        .flowOn(dispatcher)
        .asResource()
}


fun <T> Flow<T>.asResource(): Flow<Resource<T>> {
    return map<T, Resource<T>> { Resource.Success(it) }
        .onStart { emit(Resource.Loading) }
        .catch { e ->
            when (e) {
                is HttpException -> {
                    emit(Resource.Error(Throwable(e.message())))
                }

                is IOException -> {
                    emit(Resource.Error(Throwable(CONNECTION_ERROR)))
                }

                else -> {
                    emit(Resource.Error(e))
                }
            }
        }
}


inline fun <F : Fragment, T : Any?> F.collectFlowWithLifeCycle(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend (T) -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow.flowWithLifecycle(viewLifecycleOwner.lifecycle, state).collect {
            block(it)
        }
    }
}

inline fun <A : AppCompatActivity, T : Any?> A.collectFlowWithLifeCycle(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend (T) -> Unit,
) {
    lifecycleScope.launch {
        flow.flowWithLifecycle(lifecycle, state).collect {
            block(it)
        }
    }
}