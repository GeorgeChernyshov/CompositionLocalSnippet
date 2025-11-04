package com.example.compositionlocalsnippet.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compositionlocalsnippet.R
import com.example.compositionlocalsnippet.components.AppBar
import com.example.compositionlocalsnippet.theme.Purple40
import com.example.compositionlocalsnippet.theme.Purple80


enum class ThemeMode { LIGHT, DARK }

val LocalUserName = compositionLocalOf<String> { error("No user name provided") }
val LocalThemeMode = compositionLocalOf { ThemeMode.LIGHT }

@Composable
fun BasicsScreen(onNextClick: () -> Unit) {
    var currentTheme by remember { mutableStateOf(ThemeMode.LIGHT) }

    Scaffold(
        topBar = { AppBar(name = stringResource(R.string.label_basics_screen)) }
    ) { paddingValues ->
        CompositionLocalProvider(
            LocalUserName provides "Max",
            LocalThemeMode provides currentTheme
        ) {
            val backgroundColor = if (LocalThemeMode.current == ThemeMode.LIGHT)
                Color(0xFFFFFFFF)
            else Color(0xFF000000)

            val buttonBackgroundColor = if (LocalThemeMode.current == ThemeMode.LIGHT)
                Purple40
            else Purple80

            val buttonTextColor = if (LocalThemeMode.current == ThemeMode.LIGHT)
                Color(0xFFFFFFFF)
            else Color(0xFF000000)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Greeting()

                ThemeSwitcher(
                    onToggleTheme = {
                        currentTheme = if (currentTheme == ThemeMode.LIGHT)
                            ThemeMode.DARK
                        else ThemeMode.LIGHT
                    }
                )

                Button(
                    onClick = onNextClick,
                    colors = ButtonColors(
                        containerColor = buttonBackgroundColor,
                        contentColor = buttonTextColor,
                        disabledContainerColor = buttonBackgroundColor,
                        disabledContentColor = buttonTextColor
                    )
                ) {
                    Text(text = stringResource(R.string.button_next))
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    Column {
        WelcomeMessage()
        UserDetails()
    }
}

@Composable
fun WelcomeMessage() {
    val textColor = if (LocalThemeMode.current == ThemeMode.LIGHT)
        Color(0xFF000000)
    else Color(0xFFFFFFFF)

    // 3. Consume the value
    val userName = LocalUserName.current
    Text(
        text = stringResource(R.string.basics_welcome, userName),
        color = textColor
    )
}

@Composable
fun UserDetails() {
    val textColor = if (LocalThemeMode.current == ThemeMode.LIGHT)
        Color(0xFF000000)
    else Color(0xFFFFFFFF)

    Column {
        Text(
            text = stringResource(R.string.basics_details),
            color = textColor
        )

        // 4. Consume the value again, deeper in the tree
        Text(
            text = "Name: ${LocalUserName.current}",
            color = textColor
        )

        InnerComponent()
    }
}


@Composable
fun InnerComponent() {
    val textColor = if (LocalThemeMode.current == ThemeMode.LIGHT)
        Color(0xFF000000)
    else Color(0xFFFFFFFF)

    Text(
        text = stringResource(
            R.string.basics_inner_component_hint,
            LocalUserName.current
        ),
        color = textColor
    )
}

@Composable
fun ThemeSwitcher(onToggleTheme: () -> Unit) {
    val textColor = if (LocalThemeMode.current == ThemeMode.LIGHT)
        Color(0xFF000000)
    else Color(0xFFFFFFFF)

    val buttonBackgroundColor = if (LocalThemeMode.current == ThemeMode.LIGHT)
        Purple40
    else Purple80

    val buttonTextColor = if (LocalThemeMode.current == ThemeMode.LIGHT)
        Color(0xFFFFFFFF)
    else Color(0xFF000000)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onToggleTheme,
            colors = ButtonColors(
                containerColor = buttonBackgroundColor,
                contentColor = buttonTextColor,
                disabledContainerColor = buttonBackgroundColor,
                disabledContentColor = buttonTextColor
            )
        ) {
            Text(stringResource(R.string.basics_toggle_theme))
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = stringResource(
                R.string.basics_current_theme,
                LocalThemeMode.current
            ),
            color = textColor
        )
    }
}