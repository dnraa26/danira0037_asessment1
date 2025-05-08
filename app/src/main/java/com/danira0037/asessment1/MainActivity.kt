package com.danira0037.asessment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.danira0037.asessment1.navigation.SetupNavGraph
import com.danira0037.asessment1.ui.theme.Asessment1Theme
import com.danira0037.asessment1.ui.theme.ThemeOption
import com.danira0037.asessment1.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val settingsDataStore = remember { SettingsDataStore(this) }
            val theme = settingsDataStore.themeFlow.collectAsState(initial = ThemeOption.LightTheme.name).value

            Asessment1Theme(
                theme = theme,
                content = {
                    SetupNavGraph()
                }
            )
        }
    }
}

