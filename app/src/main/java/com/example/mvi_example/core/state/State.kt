package com.example.mvi_example.core.state

sealed class State<out R> {
    object Loading : State<Nothing>()
    object LoadingNextPage : State<Nothing>()
    class LoadingFailed(val error: Throwable?) : State<Nothing>()
    object RetryingLoad : State<Nothing>()
    class Loaded<out T>(val data: T) : State<T>()

    // Use this oldData to pass it to ManualReloadingFailed
    class ManualReloading<out T>(val oldData: T) : State<T>()
    class ManualReloadingFailed<out T>(val oldData: T, val error: Throwable?) : State<T>()
}