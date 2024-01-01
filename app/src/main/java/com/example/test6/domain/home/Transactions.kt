package com.example.test6.domain.home

data class Transactions(
    val transactionList: List<Transaction>
)

data class Transaction(
    val id: Int,
    val transactionType: TransactionType,
    val amountOfMoney: Int,
    val transactionDescription: String
) {



    enum class TransactionType{
        INCOME,
        OUTCOME,
        NONE;

        companion object{
            fun stringToTransactionType(transactionType:String) :TransactionType{
                return when(transactionType){
                    "outcome" -> {
                        OUTCOME
                    }
                    "income" -> {
                        INCOME
                    }
                    else -> {
                        NONE
                    }
                }
            }
        }
    }

}
