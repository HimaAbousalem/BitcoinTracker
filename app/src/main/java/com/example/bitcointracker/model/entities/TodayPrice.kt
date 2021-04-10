package com.example.bitcointracker.model.entities

import com.google.gson.annotations.SerializedName
import java.lang.IllegalArgumentException


data class TodayPrice(
    val bpi: LinkedHashMap<String,CurrencyRate>
)

data class CurrencyRate(
    @SerializedName("rate_float")
    val rate: Double
)

enum class CurrencyType(val currencyType: String) {
    EUR("EUR"),
    USD("USD"),
    GBP("GBP");

    companion object {
        private val map by lazy { values().associateBy(CurrencyType::currencyType) }
        operator fun get(currencyType: String) = map[currencyType] ?: throw IllegalArgumentException("Trying to access currency which doesn't exist: $currencyType")
    }
}

data class HistoricalPrice(
    val bpi: LinkedHashMap<String, Double>
)

data class PopulateData(
    val days: LinkedHashMap<String, MutableList<Currency>> = linkedMapOf()
)

data class Currency(
    val date: String,
    val rate: Double,
    val currencyType: CurrencyType
)
