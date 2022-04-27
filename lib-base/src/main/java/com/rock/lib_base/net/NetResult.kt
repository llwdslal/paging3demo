package com.rock.lib_base.net

sealed class NetResult<out T> {
    data class Success<T>(val data: T) : NetResult<T>()
    data class Error(val code:Int,val msg:String): NetResult<Nothing>()
    data class Exception(val ex:java.lang.Exception): NetResult<Nothing>()
}