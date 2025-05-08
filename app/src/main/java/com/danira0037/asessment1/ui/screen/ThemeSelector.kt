package com.danira0037.asessment1.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.danira0037.asessment1.R
import com.danira0037.asessment1.ui.theme.ThemeOption
import com.danira0037.asessment1.util.SettingsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ThemeSelector(dataStore: SettingsDataStore){
    val settingsDataStore = dataStore

    var expanded by remember { mutableStateOf(false) }


    IconButton(
        onClick = {
            expanded = true
        }
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(id = R.string.other),
            tint = MaterialTheme.colorScheme.primary
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ){
            DropdownMenuItem(
                text = { Text(text = ThemeOption.LightTheme.name) },
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        settingsDataStore.saveTheme(ThemeOption.LightTheme.name)
                    }
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text(text = ThemeOption.DarkTheme.name) },
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        settingsDataStore.saveTheme(ThemeOption.DarkTheme.name)
                    }
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text(text = ThemeOption.MediumContrastLightTheme.name) },
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        settingsDataStore.saveTheme(ThemeOption.MediumContrastLightTheme.name)
                    }
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text(text = ThemeOption.HighContrastLightTheme.name) },
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        settingsDataStore.saveTheme(ThemeOption.HighContrastLightTheme.name)
                    }
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text(text = ThemeOption.MediumContrastDarkTheme.name) },
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        settingsDataStore.saveTheme(ThemeOption.MediumContrastDarkTheme.name)
                    }
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text(text = ThemeOption.HighContrastDarkTheme.name) },
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        settingsDataStore.saveTheme(ThemeOption.HighContrastDarkTheme.name)
                    }
                    expanded = false
                }
            )
        }
    }
}