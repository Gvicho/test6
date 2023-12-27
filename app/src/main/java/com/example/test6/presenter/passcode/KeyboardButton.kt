package com.example.test6.presenter.passcode

data class KeyboardButton(
    val id:Int,
    val buttonType: ButtonType,
    val content:Int
    ) {
}

enum class ButtonType{
    NUMBER,
    ICON,
}