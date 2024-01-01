package com.example.test6.data.home

import com.example.test6.domain.home.Transaction
import com.example.test6.domain.home.Transactions
import com.example.test6.domain.home.Transaction.TransactionType.Companion.stringToTransactionType


fun TransactionsDto.toDomain():Transactions{
    var transactions = emptyList<Transaction>()

    transactionsList.forEach{transactionDto ->
        transactions+= transactionDto.toDomain()
    }

    return Transactions(
        transactionList = transactions
    )
}


fun TransactionDto.toDomain():Transaction{
    return Transaction(
        id = id,
        transactionType = stringToTransactionType(transactionType),
        amountOfMoney = amountOfMoney,
        transactionDescription = transactionDescription
    )
}