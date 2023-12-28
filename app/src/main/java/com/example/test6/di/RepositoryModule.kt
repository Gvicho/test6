package com.example.test6.di

import com.example.test6.data.passcode.PasscodeService
import com.example.test6.data.passcode.PasswordRepositoryImpl
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

}