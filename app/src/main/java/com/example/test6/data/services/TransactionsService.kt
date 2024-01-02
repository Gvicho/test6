package com.example.test6.data.services

import com.example.test6.data.model.TransactionsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TransactionsService {

    @GET("transactions")
    suspend fun getTransactions(@Query("token") token:String) : Response<TransactionsDto>

}