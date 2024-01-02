package com.example.test6.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.test6.data.repository.DataStoreRepositoryImpl
import com.example.test6.data.repository.TransactionsRepositoryImpl
import com.example.test6.data.services.TransactionsService
import com.example.test6.data.services.PasscodeService
import com.example.test6.data.repository.PasswordRepositoryImpl
import com.example.test6.domain.DataStoreRepository
import com.example.test6.domain.home.TransactionsRepository
import com.example.test6.domain.passcode.PasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePasswordRepository(passcodeService: PasscodeService): PasswordRepository{
        return PasswordRepositoryImpl(
            passcodeService = passcodeService
        )
    }

    @Singleton
    @Provides
    fun provideTransactionsRepository(transactionsService: TransactionsService):TransactionsRepository{
        return TransactionsRepositoryImpl(
            transactionsService = transactionsService
        )
    }

    @Singleton
    @Provides
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>):DataStoreRepository{
        return DataStoreRepositoryImpl(dataStore = dataStore)
    }

}