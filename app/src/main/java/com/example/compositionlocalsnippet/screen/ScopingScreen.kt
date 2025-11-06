package com.example.compositionlocalsnippet.screen

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compositionlocalsnippet.R
import com.example.compositionlocalsnippet.components.AppBar
import com.example.compositionlocalsnippet.theme.CompositionLocalSnippetTheme

@Composable
fun ScopingScreen() {
    Scaffold(
        topBar = { AppBar(name = stringResource(R.string.label_scoping)) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultValuesBlock()
            ScopingBlock()
        }
    }
}

@Composable
fun DefaultValuesBlock() {
//    val name = LocalUserName.current
    val analytics = LocalDynamicAnalytics.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //You'll get an error here:
        //Text("Default user name is $name")

        Text(
            text = stringResource(R.string.scoping_default_analytics_hint),
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            analytics.logEvent("Default Event")
        }) {
            Text(stringResource(R.string.scoping_default_analytics_log))
        }

        Text(stringResource(
            R.string.scoping_default_analytics_count,
            analytics.eventCount
        ))
    }
}

@Composable
fun ScopingBlock() {
    val outerOverrideService = remember { AnalyticsService() }
    CompositionLocalProvider(LocalDynamicAnalytics provides outerOverrideService) {
        Text(
            text = stringResource(R.string.scoping_outer),
            modifier = Modifier.fillMaxWidth()
        )

        AnalyticsLogger(type = AnalyticsType.DYNAMIC) // Reads outerOverrideService

        Spacer(Modifier.height(8.dp))

        val innerOverrideService = remember { AnalyticsService() }
        CompositionLocalProvider(LocalDynamicAnalytics provides innerOverrideService) {
            Text(
                text = stringResource(R.string.scoping_inner),
                modifier = Modifier.fillMaxWidth()
            )

            AnalyticsLogger(type = AnalyticsType.DYNAMIC) // Reads innerOverrideService
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultValuesBlockPreview() {
    CompositionLocalSnippetTheme {
        DefaultValuesBlock()
    }
}

@Composable
fun ScopingBlockPreview() {
    CompositionLocalSnippetTheme {
        ScopingBlock()
    }
}