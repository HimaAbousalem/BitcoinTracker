package com.example.bitcointracker.features

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bitcointracker.model.entities.PopulateData

class SharedViewModel : ViewModel() {

    val bitCoinPriceDetails = MutableLiveData<PopulateData>()

    fun setData(data: PopulateData) {
        bitCoinPriceDetails.value = data
    }
}