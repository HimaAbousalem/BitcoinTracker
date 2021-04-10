package com.example.bitcointracker.model.usecase

import com.example.bitcointracker.model.entities.*
import com.example.bitcointracker.model.entities.Currency
import com.example.bitcointracker.model.network.BitcoinApi
import com.example.bitcointracker.util.TWO_WEEKS_AGO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class BitcoinUseCase @Inject constructor(
    private val bitcoinApi: BitcoinApi
) {
    suspend fun retrieveData(): PopulateData {
        val populateData =
            PopulateData(linkedMapOf())
        return withContext(Dispatchers.IO) {

            val currentDay = bitcoinApi.getCurrentDayPrice()

            val historicalUSD = getHistoricalData("USD")
            val historicalEUR = getHistoricalData("EUR")
            val historicalGBP = getHistoricalData("GBP")

            fillCurrentDayData(currentDay, populateData)
            fillHistorical(
                historicalEUR,
                CurrencyType["EUR"],
                populateData
            )
            fillHistorical(
                historicalUSD,
                CurrencyType["USD"],
                populateData
            )
            fillHistorical(
                historicalGBP,
                CurrencyType["GBP"],
                populateData
            )
            populateData
        }
    }

    private suspend fun getHistoricalData(currencyType: String): HistoricalPrice {
        return bitcoinApi.getLastTwoWeeksData(
            getTwoWeeksAgoDate(),
            getFormattedDate(System.currentTimeMillis()),
            currencyType
        )
    }

    private fun fillHistorical(
        historical: HistoricalPrice,
        currencyType: CurrencyType,
        populateData: PopulateData
    ) {
        historical.bpi.asIterable().reversed().forEach {
            if (populateData.days.containsKey(it.key)) {
                populateData.days[it.key]?.add(Currency(it.key, it.value, currencyType))
            } else {
                populateData.days[it.key] = mutableListOf(Currency(it.key, it.value, currencyType))
            }
        }
    }

    private fun fillCurrentDayData(
        todayPrice: TodayPrice,
        populateData: PopulateData
    ) {
        val currentDate = getFormattedDate(System.currentTimeMillis())
        val currencyList = mutableListOf<Currency>()
        todayPrice.bpi.forEach {
            currencyList.add(
                Currency(
                    "Today",
                    it.value.rate,
                    CurrencyType[it.key]
                )
            )
        }
        populateData.days[currentDate] = currencyList
    }

    private fun getFormattedDate(timeInMillis: Long): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return simpleDateFormat.format(timeInMillis)
    }

    private fun getTwoWeeksAgoDate(): String {
        val calendar = Calendar.getInstance(Locale.US)
        calendar.add(Calendar.DAY_OF_YEAR, TWO_WEEKS_AGO)
        return getFormattedDate(calendar.timeInMillis)
    }

}