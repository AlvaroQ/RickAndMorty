package com.rickandmorty.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    internal val baseRoute: String,
    private val navArgs: List<NavArgs> = emptyList()
) {
    object Home : NavItem("home")
    object Detail : NavItem("detail", listOf(NavArgs.CharacterId)) {
        fun createRoute(characterId: Int) = "$baseRoute/$characterId"
    }

    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

}

enum class NavArgs(val key: String, val navType: NavType<*>) {
    CharacterId("characterId", NavType.IntType)
}