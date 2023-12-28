package com.example.test6.data.passcode

import com.squareup.moshi.Json

data class PasscodeRequest(
    @Json(name = "password")
    val passcode: String
)