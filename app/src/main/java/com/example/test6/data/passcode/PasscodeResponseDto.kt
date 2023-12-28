package com.example.test6.data.passcode

import com.squareup.moshi.Json

data class PasscodeResponseDto(
    @Json(name = "token")
    val token:String
) {
}