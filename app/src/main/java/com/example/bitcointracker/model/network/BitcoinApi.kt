package com.example.bitcointracker.model.network

import com.example.bitcointracker.model.entities.HistoricalPrice
import com.example.bitcointracker.model.entities.TodayPrice
import retrofit2.http.GET
import retrofit2.http.Query

interface BitcoinApi {

    @GET("v1/bpi/currentprice.json")
    suspend fun getCurrentDayPrice(): TodayPrice

    @GET("v1/bpi/historical/close.json")
    suspend fun getLastTwoWeeksData(
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("currency") currency: String
    ): HistoricalPrice
}