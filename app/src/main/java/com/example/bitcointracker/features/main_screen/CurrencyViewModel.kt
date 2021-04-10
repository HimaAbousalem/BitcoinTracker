package com.example.bitcointracker.features.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.bitcointracker.model.entities.Error
import com.example.bitcointracker.model.entities.Loading
import com.example.bitcointracker.model.entities.Success
import com.example.bitcointracker.model.usecase.BitcoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val useCase: BitcoinUseCase) : ViewModel() {

    fun getHistoricalData() = liveData(Dispatchers.IO) {
        emit(Loading)
        try {
            val data = useCase.retrieveData()
            emit(Success(data))
        } catch (exception: Exception) {
            emit(Error(exception.localizedMessage ?: "No Internet!"))
        }
    }
}