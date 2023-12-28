package com.example.test6.data.passcode

import com.example.test6.domain.passcode.PasscodeResponse

fun PasscodeResponseDto.toDomain():PasscodeResponse{
    return PasscodeResponse(
        token = token
    )
}