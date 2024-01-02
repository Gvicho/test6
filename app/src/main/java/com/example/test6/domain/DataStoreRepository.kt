package com.example.test6.domain

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow


interface DataStoreRepository {

    suspend fun saveToken(key: Preferences.Key<String>,tokenValue: String)

    suspend fun readToken(key: Preferences.Key<String>) :Flow<String>

    suspend fun clear()

}