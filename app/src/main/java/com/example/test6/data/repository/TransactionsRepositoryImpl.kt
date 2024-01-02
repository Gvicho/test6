package com.example.test6.data.repository

import android.util.Log
import com.example.test6.data.common.ResultWrapper
import com.example.test6.data.services.TransactionsService
import com.example.test6.data.mapper.toDomain
import com.example.test6.domain.home.TransactionsRepository
import com.example.test6.domain.home.Transactions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TransactionsRepositoryImpl@Inject constructor(private val transactionsService: TransactionsService): TransactionsRepository {

    override suspend fun getTransactions(token:String): Flow<ResultWrapper<Transactions>> {
        return flow {
            emit(ResultWrapper.Loading(loading = true))
            Log.d("tag123", "Transactions Loading")
            try {
                val response = transactionsService.getTransactions(token)
                if(response.isSuccessful){
                    Log.d("tag123", "Transactions Success")
                    emit( ResultWrapper.Success( data = response.body()!!.toDomain() ) )
                }else{
                    Log.d("tag123", "Transactions Error")
                    emit( ResultWrapper.Error(errorMessage = response.errorBody()?.string() ?: "emptyError") )
                }
            }catch (e:Throwable){

                Log.d("tag123", "Transactions ${e.message}")
            }
            emit(ResultWrapper.Loading(loading = false))
        }
    }

}