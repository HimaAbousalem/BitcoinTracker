package com.example.bitcointracker.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bitcointracker.model.entities.Currency
import com.example.bitcointracker.model.entities.PopulateData

@Dao
interface BitcoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dataList: List<PopulateData>)

    @Query("DELETE FROM BitCoinTable")
    suspend fun deleteAll()

    @Query("SELECT * FROM BitCoinTable")
    suspend fun getAllData(): List<PopulateData>

    @Query("SELECT * from BitCoinTable LIMIT 1")
    suspend fun getTodayRow(): PopulateData?
}