package com.example.mvi_example.core.state

sealed class Event<out R> {
    object RetryInitialLoad : Event<Nothing>()
    object ManualLoad : Event<Nothing>()
    object LoadNextPage : Event<Nothing>()
    class LoadSuccess<out T>(val data: T) : Event<T>()
    class LoadFailure(val error: Throwable?) : Event<Nothing>()
}