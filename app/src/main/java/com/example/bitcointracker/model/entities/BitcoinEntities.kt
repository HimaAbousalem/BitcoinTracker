package com.example.bitcointracker.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


data class TodayPrice(
    val bpi: LinkedHashMap<String, CurrencyRate>
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
        operator fun get(currencyType: String) = map[currencyType]
            ?: throw IllegalArgumentException("Trying to access currency which doesn't exist: $currencyType")
    }
}

data class HistoricalPrice(
    val bpi: LinkedHashMap<String, Double>
)

@Entity(tableName = "BitCoinTable")
data class PopulateData(
    @PrimaryKey
    val date: String,
    val currencies: MutableList<Currency>
)

data class Currency(
    val date: String,
    val rate: Double,
    val currencyType: CurrencyType
)

class CurrencyTypeConverter {

    @TypeConverter
    fun toCurrencyType(value: String) = CurrencyType[value]

    @TypeConverter
    fun fromCurrencyType(value: CurrencyType) = value.currencyType
}


class CurrencyListConverters {
    @TypeConverter
    fun fromString(value: String): List<Currency> {
        val listType = object : TypeToken<ArrayList<Currency>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Currency>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}