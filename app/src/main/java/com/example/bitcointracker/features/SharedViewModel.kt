package com.example.bitcointracker.features

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitcointracker.model.entities.Currency

class SharedViewModel: ViewModel(){

    val bitCoinPriceDetails = MutableLiveData<List<Currency>>()

    fun setData(data: List<Currency>){
        bitCoinPriceDetails.value = data
    }
}