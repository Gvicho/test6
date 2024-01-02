package com.example.test6.data.model

import com.squareup.moshi.Json

data class PasscodeRequest(
    @Json(name = "password")
    val passcode: String
)