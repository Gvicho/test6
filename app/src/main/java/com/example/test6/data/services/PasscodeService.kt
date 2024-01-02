package com.example.test6.data.services

import com.example.test6.data.model.PasscodeRequest
import com.example.test6.data.model.PasscodeResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PasscodeService {
    @POST("login")
    suspend fun login(@Body passcode: PasscodeRequest): Response<PasscodeResponseDto>
}