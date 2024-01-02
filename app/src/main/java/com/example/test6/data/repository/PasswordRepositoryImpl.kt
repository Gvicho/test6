package com.example.test6.data.repository

import android.util.Log.d
import com.example.test6.data.common.ResultWrapper
import com.example.test6.data.model.PasscodeRequest
import com.example.test6.data.services.PasscodeService
import com.example.test6.data.mapper.toDomain
import com.example.test6.domain.passcode.PasscodeResponse
import com.example.test6.domain.passcode.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(private val passcodeService: PasscodeService): PasswordRepository {
    override suspend fun logIn(
        password: String
    ): Flow<ResultWrapper<PasscodeResponse>> {
        return flow{
            emit(ResultWrapper.Loading(loading = true))
            d("tag123","Loading")
            try {
                val response = passcodeService.login(PasscodeRequest(password))
                if(response.isSuccessful){
                    d("tag123","Success")
                    emit( ResultWrapper.Success( data = response.body()!!.toDomain() ) )
                }else{
                    d("tag123","Error")
                    emit( ResultWrapper.Error(errorMessage = response.errorBody()?.string() ?: "emptyError") )
                }
            }catch (e:Throwable){

                d("tag123","${e.message}")
            }
            emit(ResultWrapper.Loading(loading = false))
        }
    }
}