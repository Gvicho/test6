package com.example.test6.di

import com.example.test6.data.home.TransactionsRepositoryImpl
import com.example.test6.data.home.TransactionsService
import com.example.test6.data.passcode.PasscodeService
import com.example.test6.data.passcode.PasswordRepositoryImpl
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

}