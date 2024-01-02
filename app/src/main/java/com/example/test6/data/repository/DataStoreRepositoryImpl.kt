package com.example.test6.data.repository

import android.util.Log.d
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.test6.datastore.DataStoreUtils
import com.example.test6.domain.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): DataStoreRepository {
    override suspend fun saveToken(key: Preferences.Key<String>, tokenValue: String) {
        dataStore.edit { userInfo ->
            userInfo[key] = tokenValue
        }
    }

    override suspend fun readToken(key: Preferences.Key<String>): Flow<String> = dataStore.data
        .map {preferances ->
            preferances[key] ?: ""
        }

    override suspend fun clear(){
        try {
            dataStore.edit { settings ->
                settings.remove(DataStoreUtils.TOKEN)
                d("tag123", "DataStore cleared successfully")
            }
        } catch (e: Exception) {
            d("tag123", "Error clearing DataStore $e")
        }
    }
}