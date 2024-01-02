package com.example.test6.presenter.splash

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test6.datastore.DataStoreUtils
import com.example.test6.domain.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository)  :ViewModel() {
    private val _sessionFlow = MutableSharedFlow<SplashState>()
    val sessionFlow : SharedFlow<SplashState> get() = _sessionFlow


    fun readSession(){
        viewModelScope.launch {
            delay(1000)
            dataStoreRepository.readToken(DataStoreUtils.TOKEN).collect{
                val token = it
                if(token.isNotEmpty()){
                    _sessionFlow.emit( SplashState(true, token) )
                    d("tag123","$token ->token")
                }else{
                    _sessionFlow.emit( SplashState() ) // default values
                }

            }
        }
    }

}