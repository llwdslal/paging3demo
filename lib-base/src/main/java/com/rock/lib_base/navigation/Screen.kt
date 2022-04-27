package com.rock.lib_base.navigation

import androidx.navigation.*

abstract class Screen(private val _route:String){

    abstract val rootRoute:String?

    open val arguments:List<NamedNavArgument> = emptyList()
    open val deepLinks:List<NavDeepLink> = emptyList()

    val route:String
        get() = if (rootRoute == null) _route else "${rootRoute}/${_route}"

}

object PageNotFoundScreen:Screen("pageNotFound"){
    override val rootRoute: String
        get() = "route404"
}






