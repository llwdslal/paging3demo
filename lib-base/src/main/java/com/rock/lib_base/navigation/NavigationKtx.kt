package com.rock.lib_base.navigation

import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.createRoute
import androidx.navigation.compose.composable

fun NavGraphBuilder.composableScreen(
    screen: Screen,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = screen.route,
        arguments = screen.arguments,
        deepLinks = screen.deepLinks,
        content = content
    )
}

val NavController.pageNotFoundRequest
    get() = NavDeepLinkRequest.Builder.fromUri(createRoute(PageNotFoundScreen.route).toUri()).build()

val NavController.hasPageNotFoundRequest
    get() = this.graph.matchDeepLink(pageNotFoundRequest) != null

fun NavController.navigateEx(
    route: String,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {

    val request = NavDeepLinkRequest.Builder.fromUri(createRoute(route).toUri()).build()
    val requestMatch = this.graph.matchDeepLink(request)

    if (requestMatch == null && hasPageNotFoundRequest) {
        this.navigate(pageNotFoundRequest, navOptions, navigatorExtras)
    } else {
        this.navigate(request, navOptions, navigatorExtras)
    }
}
