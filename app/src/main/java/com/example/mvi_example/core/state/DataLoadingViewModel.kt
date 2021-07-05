package com.example.mvi_example.core.state

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class DataLoadingViewModel<T> : ViewModel() {

    var state = MutableLiveData<State<T>>(State.Loading)

    abstract suspend fun load(): Result<T>

    fun proceedToLoad() = viewModelScope.launch {
        when (val result = load()) {
            is Result.Success -> {
                transition(Event.LoadSuccess(result.value))
            }
            is Result.Failure -> {
                transition(Event.LoadFailure(result.error))
            }
        }
    }

    /* Actions */
    fun retryInitialLoad() {
        transition(Event.RetryInitialLoad)
    }

    fun reloadManually() {
        transition(Event.ManualLoad)
    }

    fun loadNextPage() {
        transition(Event.LoadNextPage)
    }

    // Transition of the state machine.
    private fun transition(event: Event<T>) {
        when {
            state.value is State.Loading && event is Event.LoadSuccess -> {
                state.value = State.Loaded(event.data)
            }

            state.value is State.Loading && event is Event.LoadFailure -> {
                state.value = State.LoadingFailed(event.error)
            }

            state.value is State.Loaded && event is Event.LoadNextPage -> {
                state.value = State.LoadingNextPage
                // TODO: Add load next page function.
            }

            state.value is State.LoadingFailed && event is Event.RetryInitialLoad -> {
                state.value = State.RetryingLoad
                proceedToLoad()
            }

            state.value is State.RetryingLoad && event is Event.LoadSuccess -> {
                state.value = State.Loaded(event.data)
            }

            state.value is State.RetryingLoad && event is Event.LoadFailure -> {
                state.value = State.LoadingFailed(event.error)
            }

            state.value is State.Loaded && event is Event.ManualLoad -> {
                val oldData = (state.value as State.Loaded<T>).data
                state.postValue(State.ManualReloading(oldData))
                proceedToLoad()
            }

            state.value is State.ManualReloading && event is Event.LoadSuccess -> {
                state.value = State.Loaded(event.data)
            }

            state.value is State.ManualReloading && event is Event.LoadFailure -> {
                val oldData = (state.value as State.ManualReloading<T>).oldData
                state.value = State.ManualReloadingFailed(oldData, event.error)
            }

            state.value is State.ManualReloadingFailed && event is Event.ManualLoad -> {
                val oldData = (state.value as State.ManualReloadingFailed<T>).oldData
                state.postValue(State.ManualReloading(oldData))
                proceedToLoad()
            }

            else -> {
                Log.e("data", "invalid event $event for state ${state.value} ")
            }
        }
    }
}
