package com.example.test6.domain.passcode

import com.example.test6.data.common.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {
    suspend fun logIn(password:String): Flow<ResultWrapper<PasscodeResponse>>
}