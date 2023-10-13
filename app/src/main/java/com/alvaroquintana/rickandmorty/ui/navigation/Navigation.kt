package com.alvaroquintana.rickandmorty.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alvaroquintana.rickandmorty.ui.screens.detail.DetailScreen
import com.alvaroquintana.rickandmorty.ui.screens.home.HomeScreen
import com.alvaroquintana.rickandmorty.ui.screens.splash.SplashScreen

const val TRANSITION_TIME = 300

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavItem.Splash.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(TRANSITION_TIME)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(durationMillis = TRANSITION_TIME)
            )
        }
    ) {
        composable(NavItem.Splash) {
            SplashScreen(onNavigate = {
                navController.navigate(NavItem.Home.route)
            })
        }
        composable(NavItem.Home) {
            HomeScreen(onNavigate = {
                navController.navigate(NavItem.Detail.createRoute(it))
            })
        }
        composable(NavItem.Detail) { backStackEntry ->
            DetailScreen(
                characterId = backStackEntry.findArg(NavArgs.CharacterId.key),
                onUpClick = { navController.popBackStack() }
            )
        }
    }
}

private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navItem.route,
        arguments = navItem.args
    ) {
        content(it)
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(key: String): T {
    val value = arguments?.getInt(key)
    requireNotNull(value)
    return value as T
}