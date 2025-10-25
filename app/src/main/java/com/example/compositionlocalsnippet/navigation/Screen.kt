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
}