/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rock.lib_base.arch


import kotlinx.coroutines.flow.*
import java.util.concurrent.atomic.AtomicInteger

//copy from https://github.com/chrisbanes/tivi.git

typealias ExceptionHandler = (error:Throwable)->Unit
typealias NetExceptionHandler = (error:NetRequestException)->Unit

class ObservableLoadingCounter {
    private val count = AtomicInteger()
    private val loadingState = MutableStateFlow(count.get())

    val flow: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addLoader() {
        loadingState.value = count.incrementAndGet()
    }

    fun removeLoader() {
        loadingState.value = count.decrementAndGet()
    }
}

suspend fun Flow<InvokeStatus>.collectStatus(
    counter: ObservableLoadingCounter,
    netExHandler: NetExceptionHandler? = null,
    exHandler:ExceptionHandler? = null
) {
    this.collect { status ->
        when(status){
            InvokeStarted -> counter.addLoader()
            InvokeSuccess -> counter.removeLoader()
            is InvokeError ->{
                if (status.throwable is NetRequestException){
                    netExHandler?.invoke(status.throwable)
                }else{
                    exHandler?.invoke(status.throwable)
                }
            }
        }
    }
}
