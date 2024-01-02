package com.example.test6.presenter.passcode

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.R
import com.example.test6.domain.passcode.PasswordRepository
import com.example.test6.data.common.ResultWrapper
import com.example.test6.datastore.DataStoreUtils
import com.example.test6.domain.DataStoreRepository
import com.example.test6.domain.passcode.PasscodeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val passwordRepository:PasswordRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _passwordFlow = MutableStateFlow<PasswordScreenState>(PasswordScreenState())
    val passwordFlow: StateFlow<PasswordScreenState> = _passwordFlow

    val buttonList = listOf(
        KeyboardButton(1,ButtonType.NUMBER,1),
        KeyboardButton(2,ButtonType.NUMBER,2),
        KeyboardButton(3,ButtonType.NUMBER,3),
        KeyboardButton(4,ButtonType.NUMBER,4),
        KeyboardButton(5,ButtonType.NUMBER,5),
        KeyboardButton(6,ButtonType.NUMBER,6),
        KeyboardButton(7,ButtonType.NUMBER,7),
        KeyboardButton(8,ButtonType.NUMBER,8),
        KeyboardButton(9,ButtonType.NUMBER,9),
        KeyboardButton(10,ButtonType.ICON, R.drawable.ic_touch),
        KeyboardButton(11,ButtonType.NUMBER,0),
        KeyboardButton(12,ButtonType.ICON, R.drawable.ic_delete)
    )

    fun addDigit(digit: Int) {
        val currentPasswordState = _passwordFlow.value
        val passwordList = currentPasswordState.password
        if(passwordList.size in 0..3){
            viewModelScope.launch {
                val newPasswordScreenState = PasswordScreenState(passwordList+digit)
                _passwordFlow.emit(newPasswordScreenState)
            }
            if(passwordList.size == 3)checkPassword()
        }
    }

    fun deleteLastDigit() {
        val currentPasswordState = _passwordFlow.value
        val passwordList = currentPasswordState.password
        if(passwordList.size in 1..4){
            viewModelScope.launch {
                _passwordFlow.emit(PasswordScreenState(passwordList.dropLast(1)))
            }
        }

    }

    private fun saveDataStore(token:String){
        viewModelScope.launch {
            dataStoreRepository.saveToken(DataStoreUtils.TOKEN,token)
        }
    }

    private fun checkPassword(){
        val currentPasswordState = _passwordFlow.value
        val passwordList = currentPasswordState.password
        val password = passwordList.joinToString("")

        var resultWrapper: ResultWrapper<PasscodeResponse>?
        viewModelScope.launch {
            if(passwordList == listOf(0,9,3,4)){
                passwordRepository.logIn(password).collect{
                    when(it){
                        is ResultWrapper.Success ->{

                            val passcodeResponse = it.data!!

                            resultWrapper = ResultWrapper.Success(data = passcodeResponse)
                            val newPasswordScreenState = PasswordScreenState(
                                emptyList(),
                                resultWrapper
                            )
                            d("tag123","success ${it.data}")

                            saveDataStore(passcodeResponse.token)

                            _passwordFlow.emit(
                                newPasswordScreenState
                            )
                        }
                        is ResultWrapper.Error -> {
                            resultWrapper = ResultWrapper.Error(errorMessage = it.errorMessage!!)
                            val newPasswordScreenState = PasswordScreenState(
                                emptyList(),
                                resultWrapper
                            )
                            d("tag123","not success ${it.errorMessage}")
                            _passwordFlow.emit(
                                newPasswordScreenState
                            )
                        }
                        is ResultWrapper.Loading -> {
                            resultWrapper = ResultWrapper.Loading(loading = it.loading)
                            val newPasswordScreenState = PasswordScreenState(
                                emptyList(),
                                resultWrapper
                            )
                            _passwordFlow.emit(
                                newPasswordScreenState
                            )
                        }
                    }
                }
            }else{
                d("tag123","$passwordList")
                resultWrapper = ResultWrapper.Error(
                    "Incorrect Passcode"
                )
                val newPasswordScreenState = PasswordScreenState(
                    emptyList(),
                    resultWrapper
                )
                _passwordFlow.emit(
                    newPasswordScreenState
                )
            }

        }
    }
}