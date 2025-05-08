package com.danira0037.asessment1.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.danira0037.asessment1.ui.theme.ThemeOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_preference")

class SettingsDataStore(private val context: Context) {

    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")
        private val THEME_KEY = stringPreferencesKey("theme_key")
    }

    val layoutFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LIST] ?: true
    }

    val themeFlow : Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: ThemeOption.LightTheme.name
    }

    suspend fun saveLayout(isList : Boolean){
        context.dataStore.edit { preferences ->
            preferences[IS_LIST] = isList
        }

    }

    suspend fun saveTheme(theme : String){
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }

    }
}