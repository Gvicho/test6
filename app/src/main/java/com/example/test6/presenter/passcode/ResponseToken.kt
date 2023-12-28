package com.example.test6.presenter.passcode

import com.squareup.moshi.Json

data class ResponseToken(
    @Json(name = "token")
    val token:String
) {
}