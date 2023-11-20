package com.ogzkesk.core.base.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ogzkesk.core.base.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

fun <T> Flow<T>.asResource(): Flow<Resource<T>> {
    return map<T, Resource<T>> { Resource.Success(it) }
        .onStart { emit(Resource.Loading) }
        .catch { emit(Resource.Error(it)) }
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

inline fun <F : Fragment,T: Any> F.collectFlowWithLifeCycle(
    flow: Flow<T>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    state : Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend (T) -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch(dispatcher) {
        flow.flowWithLifecycle(viewLifecycleOwner.lifecycle,state).collect {
            block(it)
        }
    }
}

inline fun <A : AppCompatActivity,T: Any> A.collectFlowWithLifeCycle(
    flow: Flow<T>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    state : Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend (T) -> Unit,
) {
    lifecycleScope.launch(dispatcher) {
        flow.flowWithLifecycle(lifecycle,state).collect {
            block(it)
        }
    }
}