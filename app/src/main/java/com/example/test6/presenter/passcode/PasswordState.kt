package com.example.test6.presenter.passcode

import com.example.test6.data.common.ResultWrapper
import com.example.test6.domain.passcode.PasscodeResponse

data class PasswordState(
    val password:List<Int> = emptyList(),
    val result: ResultWrapper<PasscodeResponse>? = null
) {
}