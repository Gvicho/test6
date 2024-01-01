package com.example.test6.presenter.home

import com.example.test6.data.common.ResultWrapper
import com.example.test6.domain.home.Transactions

data class HomePageState(
    val transactions: ResultWrapper<Transactions>
) {
}