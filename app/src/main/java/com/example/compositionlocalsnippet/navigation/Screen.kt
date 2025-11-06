package com.example.compositionlocalsnippet.navigation

import androidx.annotation.StringRes
import com.example.compositionlocalsnippet.R
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String, @StringRes val resourceId: Int) {

    @Serializable
    data object Basics : Screen(
        route = "basics",
        resourceId = R.string.label_basics_screen
    )

    @Serializable
    data object Scoping : Screen(
        route = "scoping",
        resourceId = R.string.label_scoping
    )

    @Serializable
    data object StaticComposition : Screen(
        route = "staticComposition",
        resourceId = R.string.label_static_composition
    )
}