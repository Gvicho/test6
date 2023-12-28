package com.example.test6.presenter.passcode

import com.example.test6.presenter.common.ResultWrapper

data class PasswordState(
    val password:List<Int> = emptyList(),
    val result: ResultWrapper<ResponseToken>? = null
) {
}