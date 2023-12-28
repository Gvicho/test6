package com.example.test6.presenter.passcode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.presenter.common.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PasswordViewModel : ViewModel() {

    private val _passwordFlow = MutableStateFlow<PasswordState>(PasswordState())
    val passwordFlow: StateFlow<PasswordState> = _passwordFlow

    fun addDigit(digit: Int) {
        val currentPasswordState = _passwordFlow.value
        val passwordList = currentPasswordState.password
        if(passwordList.size in 0..3){
            viewModelScope.launch {
                val newPasswordState = PasswordState(passwordList+digit)
                _passwordFlow.emit(newPasswordState)
            }
            if(passwordList.size == 3)checkPassword()
        }
    }

    fun deleteLastDigit() {
        val currentPasswordState = _passwordFlow.value
        val passwordList = currentPasswordState.password
        if(passwordList.size in 1..4){
            viewModelScope.launch {
                _passwordFlow.emit(PasswordState(passwordList.dropLast(1)))
            }
        }

    }

    private fun checkPassword(){
        val currentPasswordState = _passwordFlow.value
        val passwordList = currentPasswordState.password

        var resultWrapper: ResultWrapper<ResponseToken>? = null
        if(passwordList == listOf(0,9,3,4)){
            Log.d("tag123","success")
            resultWrapper = ResultWrapper.Success(
                ResponseToken("")
            )
        }else{
            Log.d("tag123","$passwordList")
            resultWrapper = ResultWrapper.Error(
                ""
            )
        }
        val newPasswordState = PasswordState(
            emptyList(),
            resultWrapper
            )
        viewModelScope.launch {
            _passwordFlow.emit(
                newPasswordState
            )
        }
    }
}