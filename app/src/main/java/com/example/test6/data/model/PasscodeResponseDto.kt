package com.example.test6.data.model

import com.squareup.moshi.Json

data class PasscodeResponseDto(
    @Json(name = "token")
    val token:String
) {
}