package com.example.bitcointracker.di

import android.content.Context
import androidx.room.Room
import com.example.bitcointracker.model.database.BitcoinDao
import com.example.bitcointracker.model.database.BitcoinRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideLocalDatabase (@ApplicationContext context: Context) : BitcoinRoom{
        return Room
            .databaseBuilder(context, BitcoinRoom::class.java, "BitCoinDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideBitCoinDao (localDatabase: BitcoinRoom) : BitcoinDao {
        return  localDatabase.bitCoinDao
    }

}