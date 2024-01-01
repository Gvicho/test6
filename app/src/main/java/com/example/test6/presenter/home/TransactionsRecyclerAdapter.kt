package com.example.test6.presenter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test6.R
import com.example.test6.databinding.TransactionItemBinding
import com.example.test6.domain.home.Transaction

class TransactionsRecyclerAdapter(): ListAdapter<Transaction, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem == newItem
            }
        }
    }



    inner class TransactionViewHolder(private val binding: TransactionItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(position: Int){
            val transaction = currentList[position]
            setTransactionTypeIcon(transaction)
            setTextForTransaction(transaction)


        }

        private fun setTextForTransaction(transaction:Transaction){
            binding.apply {
                tvId.text = transaction.id.toString()
                tvTransactionAmount.text = transaction.amountOfMoney.toString()
                tvTransactionDescription.text = transaction.transactionDescription
            }
        }

        private fun setTransactionTypeIcon(transaction:Transaction){
            when(transaction.transactionType){
                Transaction.TransactionType.INCOME -> {
                    setIcon(R.drawable.income_image)
                }
                Transaction.TransactionType.OUTCOME -> {
                    setIcon(R.drawable.outcome_image)
                }
                Transaction.TransactionType.NONE -> {

                }
            }
        }

        private fun setIcon(drawable :Int){
            binding.icIncomeOutcome.apply {
                setImageResource(drawable)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TransactionViewHolder(
            TransactionItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
            )


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TransactionViewHolder)holder.bind(position)
    }
}