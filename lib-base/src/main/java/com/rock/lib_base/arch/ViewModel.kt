package com.rock.lib_base.arch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow


interface UiAction

interface UiState

abstract class BaseViewModel<S:UiState,A:UiAction>:ViewModel(){

    protected val netLoadingCounter = ObservableLoadingCounter()

    abstract val state: StateFlow<S>

    abstract fun dispatchAction(action: A)

    open fun onNetError(ex:NetRequestException){
    }
}


