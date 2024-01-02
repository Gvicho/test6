package com.example.test6.di

import com.example.test6.data.services.TransactionsService
import com.example.test6.data.services.PasscodeService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://91ea15be-c21f-4b7b-bc9a-4c63ee80f304.mock.pstmn.io/"

    @Singleton
    @Provides
    fun provideRetrofitClient() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun providePasscodeService(retrofit: Retrofit): PasscodeService {
        return retrofit.create(PasscodeService::class.java)
    }

    @Singleton
    @Provides
    fun provideTransactionsService(retrofit: Retrofit): TransactionsService {
        return retrofit.create(TransactionsService::class.java)
    }
}