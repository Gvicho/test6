package com.example.test6.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.domain.home.TransactionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(private val transactionsRepository: TransactionsRepository) :ViewModel() {

    private val _transactionsFlow = MutableStateFlow<HomePageState?>(null)
    val transactionsFlow: StateFlow<HomePageState?> = _transactionsFlow

    fun loadTransactions(){
        viewModelScope.launch {
            transactionsRepository.getTransactions().collect{
                _transactionsFlow.value = HomePageState(
                    transactions = it
                )
            }
        }

    }



}