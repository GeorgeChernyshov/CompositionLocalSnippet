package com.example.compositionlocalsnippet.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compositionlocalsnippet.R
import com.example.compositionlocalsnippet.components.AppBar

class AnalyticsService {
    var eventCount by mutableIntStateOf(0) // Internal state to track events

    fun logEvent(name: String) {
        eventCount++
        println("Analytics: Logged event '$name'. Total events: $eventCount")
    }
}

val LocalDynamicAnalytics = compositionLocalOf { AnalyticsService() }
val LocalStaticAnalytics = staticCompositionLocalOf { AnalyticsService() }

@Composable
fun StaticCompositionScreen() {
    var dynamicService by remember {
        mutableStateOf(AnalyticsService())
    }

    var staticService by remember {
        mutableStateOf(AnalyticsService())
    }

    Scaffold(
        topBar = { AppBar(name = stringResource(R.string.label_static_composition)) }
    ) { paddingValues ->
        CompositionLocalProvider(
            LocalUserName provides "Max",
            LocalThemeMode provides ThemeMode.DARK
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.sc_inbetween_screens_hint))
                Greeting()


                Text(stringResource(R.string.sc_dynamic_hint))
                CompositionLocalProvider(LocalDynamicAnalytics provides dynamicService) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.sc_dynamic_time) + System.currentTimeMillis()
                    )

                    AnalyticsLogger(type = "Dynamic")
                }

                Text(stringResource(R.string.sc_static_hint))
                CompositionLocalProvider(LocalStaticAnalytics provides staticService) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.sc_static_time) + System.currentTimeMillis()
                    )

                    AnalyticsLogger(type = "Static")
                }

                Button(onClick = {
                    dynamicService = AnalyticsService()
                    staticService = AnalyticsService()
                }) {
                    Text(stringResource(R.string.sc_refresh))
                }
            }
        }
    }
}

@Composable
fun AnalyticsLogger(type: String) {
    val analytics = if (type == "Dynamic")
        LocalDynamicAnalytics.current
    else LocalStaticAnalytics.current

    Column(Modifier.fillMaxWidth()) {
        Button(onClick = {
            analytics.logEvent("$type Event")
        }) {
            Text("Log Event ($type)")
        }

        Text("$type service event count: ${analytics.eventCount}")
    }
}