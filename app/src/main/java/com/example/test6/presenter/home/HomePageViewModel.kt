package com.example.test6.presenter.home

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.datastore.DataStoreUtils
import com.example.test6.domain.DataStoreRepository
import com.example.test6.domain.home.TransactionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val dataStoreRepository: DataStoreRepository
) :ViewModel() {

    private val _transactionsFlow = MutableStateFlow<HomePageState?>(null)
    val transactionsFlow: StateFlow<HomePageState?> = _transactionsFlow



    fun loadTransactions(){
        viewModelScope.launch {
            //d("tag123","starting homeViewModel token retrieval")
            val token = readToken()
            //d("tag123","$token  -> homeViewModel token")

            transactionsRepository.getTransactions(token).collect{
                _transactionsFlow.value = HomePageState(
                    transactions = it
                )
            }
        }

    }

    fun clearSession(){
        GlobalScope.launch {
            d("tag123","clear datastore")
            dataStoreRepository.clear()
        }
    }

    private suspend fun readToken(): String{
        return dataStoreRepository.readToken(DataStoreUtils.TOKEN).firstOrNull() ?: ""
    }

}