package com.rock.lib_base.net

import com.rock.lib_base.arch.NetRequestException

object ResponseCode {
    val OK = 0
}

data class NetResponse<T>(val data:T,val errorCode:Int,val errorMsg:String)

suspend fun <T,R> NetResponse<T>.handleResponse(handler:suspend (T)->R):R{
    if (errorCode == ResponseCode.OK){
      return handler(this.data)
    }else{
        throw NetRequestException(errorCode,errorMsg)
    }
}