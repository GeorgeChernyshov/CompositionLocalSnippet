package com.example.compositionlocalsnippet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compositionlocalsnippet.navigation.Screen
import com.example.compositionlocalsnippet.screen.BasicsScreen
import com.example.compositionlocalsnippet.screen.StaticCompositionScreen
import com.example.compositionlocalsnippet.theme.CompositionLocalSnippetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { App() }
    }

    @Composable
    fun App() {
        val navController = rememberNavController()

        CompositionLocalSnippetTheme {
            NavHost(
                navController = navController,
                startDestination = Screen.Basics
            ) {
                composable<Screen.Basics> {
                    BasicsScreen(
                        onNextClick = { navController.navigate(Screen.StaticComposition) }
                    )
                }

                composable<Screen.StaticComposition> {
                    StaticCompositionScreen()
                }
            }
        }
    }
}