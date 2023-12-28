package com.example.test6.presenter.passcode

import com.example.test6.R

data class KeyboardButton(
    val id:Int,
    val buttonType: ButtonType,
    val content:Int
    ) {

    lateinit var iconType: IconType

    init {
        if(buttonType == ButtonType.ICON){
            iconType = when(content){
                R.drawable.ic_delete -> IconType.DELETE
                else -> IconType.FINGERPRINT
            }
        }
    }

}

enum class ButtonType{
    NUMBER,
    ICON,
}
enum class IconType{
    DELETE,
    FINGERPRINT
}