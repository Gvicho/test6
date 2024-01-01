package com.example.test6.domain.home

import com.example.test6.data.common.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {
    suspend fun getTransactions(): Flow<ResultWrapper<Transactions>>
}