package com.example.bitcointracker.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bitcointracker.model.entities.CurrencyListConverters
import com.example.bitcointracker.model.entities.CurrencyTypeConverter
import com.example.bitcointracker.model.entities.PopulateData

@TypeConverters(value = [CurrencyTypeConverter::class, CurrencyListConverters::class])
@Database(
    entities = [PopulateData::class],
    version = 1,
    exportSchema = false)
abstract class BitcoinRoom : RoomDatabase() {
    abstract val bitCoinDao : BitcoinDao
}