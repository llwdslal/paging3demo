package com.rock.lib_base.arch

import java.lang.Exception

class NetRequestException(val code:Int, message:String?):Exception(message)