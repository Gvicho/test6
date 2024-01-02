package com.example.test6.data.mapper

import com.example.test6.data.model.PasscodeResponseDto
import com.example.test6.domain.passcode.PasscodeResponse

fun PasscodeResponseDto.toDomain():PasscodeResponse{
    return PasscodeResponse(
        token = token
    )
}