package com.example.test6.data.model


import com.squareup.moshi.Json

data class TransactionsDto(
    @Json(name = "transaction-list")
    val transactionsList : List<TransactionDto>
) {
}

data class TransactionDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "transaction_type")
    val transactionType: String,
    @Json(name = "amount")
    val amountOfMoney: Int,
    @Json(name = "description")
    val transactionDescription: String
) {
}