package com.example.bitcointracker.model.repo

import com.example.bitcointracker.model.database.BitcoinDao
import com.example.bitcointracker.model.entities.*
import com.example.bitcointracker.model.entities.Currency
import com.example.bitcointracker.model.network.BitcoinApi
import com.example.bitcointracker.util.TWO_WEEKS_AGO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitcoinRepo @Inject constructor(
    private val dao: BitcoinDao,
    private val api: BitcoinApi
) {

    suspend fun getData(): List<PopulateData> {
        return if (dao.getTodayRow()?.date == getFormattedDate(System.currentTimeMillis())) {
            dao.getAllData()
        } else {
            deleteAllFromDatabase()
            val data = retrieveDataFromApi()
            dao.insertAll(data)
            data
        }
    }

    private suspend fun deleteAllFromDatabase() {
        dao.deleteAll()
    }

    private suspend fun retrieveDataFromApi(): List<PopulateData> {
        val populateDataList = mutableListOf<PopulateData>()
        return withContext(Dispatchers.IO) {

            val currentDay = api.getCurrentDayPrice()

            val historicalUSD = getHistoricalData("USD")
            val historicalEUR = getHistoricalData("EUR")
            val historicalGBP = getHistoricalData("GBP")

            fillCurrentDayData(currentDay, populateDataList)
            fillHistorical(
                historicalEUR,
                CurrencyType["EUR"],
                populateDataList
            )
            fillHistorical(
                historicalUSD,
                CurrencyType["USD"],
                populateDataList
            )
            fillHistorical(
                historicalGBP,
                CurrencyType["GBP"],
                populateDataList
            )
            populateDataList
        }
    }

    private suspend fun getHistoricalData(currencyType: String): HistoricalPrice {
        return api.getLastTwoWeeksData(
            getTwoWeeksAgoDate(),
            getFormattedDate(System.currentTimeMillis()),
            currencyType
        )
    }

    private fun fillHistorical(
        historical: HistoricalPrice,
        currencyType: CurrencyType,
        populateDataList: MutableList<PopulateData>
    ) {
        historical.bpi.asIterable().reversed().forEach { map ->
            val index = populateDataList.indexOf(populateDataList.find { it.date == map.key })
            if (index != -1) {
                populateDataList[index].currencies.add(Currency(map.key, map.value, currencyType))
            } else {
                populateDataList.add(
                    PopulateData(
                        date = map.key,
                        mutableListOf(Currency(map.key, map.value, currencyType))
                    )
                )
            }
        }
    }

    private fun fillCurrentDayData(
        todayPrice: TodayPrice,
        populateDataList: MutableList<PopulateData>
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
        populateDataList.add(PopulateData(date = currentDate, currencyList))
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