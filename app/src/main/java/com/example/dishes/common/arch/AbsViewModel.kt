package com.example.dishes.common.arch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

abstract class AbsViewModel<ViewState : Any, Action, Event>(initialViewState: ViewState) : ViewModel() {
    private val _viewStatesFlow = MutableStateFlow(initialViewState)

    private val _eventsFlow =
        MutableSharedFlow<Event?>(replay = 0, extraBufferCapacity = 1)

    fun viewStatesFlow(): StateFlow<ViewState> = _viewStatesFlow.asStateFlow()

    fun eventsFlow(): SharedFlow<Event?> = _eventsFlow.asSharedFlow()

    protected val viewState: ViewState
        get() = _viewStatesFlow.value

    protected var event: Event?
        get() = _eventsFlow.replayCache.last()
        set(value) {
            _eventsFlow.tryEmit(value)
        }

    protected fun updateViewState(function: (ViewState) -> ViewState) {
        _viewStatesFlow.update(function)
    }

    abstract fun reduce(action: Action)
}