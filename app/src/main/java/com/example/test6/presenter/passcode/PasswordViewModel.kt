package com.example.test6.presenter.passcode

import android.util.Log
import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.domain.passcode.PasswordRepository
import com.example.test6.data.common.ResultWrapper
import com.example.test6.domain.passcode.PasscodeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PasswordViewModel @Inject constructor(private val passwordRepository:PasswordRepository) : ViewModel() {

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
        val password = passwordList.joinToString("")

        var resultWrapper: ResultWrapper<PasscodeResponse>? = null
        if(passwordList == listOf(0,9,3,4)){
            d("tag123","success")
            resultWrapper = ResultWrapper.Success(
                PasscodeResponse("")
            )
        }else{

        }
        viewModelScope.launch {
            if(passwordList == listOf(0,9,3,4)){
                passwordRepository.logIn(password).collect{
                    when(it){
                        is ResultWrapper.Success ->{
                            resultWrapper = ResultWrapper.Success(data = it.data!!)
                            val newPasswordState = PasswordState(
                                emptyList(),
                                resultWrapper
                            )
                            d("tag123","success ${it.data}")
                            _passwordFlow.emit(
                                newPasswordState
                            )
                        }
                        is ResultWrapper.Error -> {
                            resultWrapper = ResultWrapper.Error(errorMessage = it.errorMessage!!)
                            val newPasswordState = PasswordState(
                                emptyList(),
                                resultWrapper
                            )
                            d("tag123","not success ${it.errorMessage}")
                            _passwordFlow.emit(
                                newPasswordState
                            )
                        }
                        is ResultWrapper.Loading -> {
                            resultWrapper = ResultWrapper.Loading(loading = it.loading)
                            val newPasswordState = PasswordState(
                                emptyList(),
                                resultWrapper
                            )
                            _passwordFlow.emit(
                                newPasswordState
                            )
                        }
                    }
                }
            }else{
                d("tag123","$passwordList")
                resultWrapper = ResultWrapper.Error(
                    "Incorrect Passcode"
                )
                val newPasswordState = PasswordState(
                    emptyList(),
                    resultWrapper
                )
                _passwordFlow.emit(
                    newPasswordState
                )
            }

        }
    }
}