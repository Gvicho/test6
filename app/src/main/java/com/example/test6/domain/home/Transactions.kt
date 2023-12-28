package com.example.test6.domain.home

data class Transactions(
    val id: Int,
    val transactionType: TransactionType,
    val amountOfMoney: Int,
    val transactionDescription: String
) {
}

enum class TransactionType{
    INCOME,
    OUTCOME
}