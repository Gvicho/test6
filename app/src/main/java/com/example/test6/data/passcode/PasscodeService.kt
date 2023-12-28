package com.example.test6.data.passcode

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PasscodeService {
    @POST("login")
    suspend fun login(@Body passcode: PasscodeRequest): Response<PasscodeResponseDto>
}