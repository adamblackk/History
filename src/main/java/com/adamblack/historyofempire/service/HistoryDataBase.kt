package com.adamblack.historyofempire.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adamblack.historyofempire.model.FeedModel
import com.adamblack.historyofempire.model.RealmModel

@Database(entities = arrayOf(FeedModel::class), version = 1)
abstract class HistoryDataBase :RoomDatabase(){
    abstract fun hidtoryDao():HistoryDao


    companion object{
    @Volatile private var instance:HistoryDataBase?=null

            private val lock=Any()

        operator fun invoke(context: Context)= instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }
        private fun makeDatabase(context: Context)= Room.databaseBuilder(context.applicationContext,
            HistoryDataBase::class.java,"historyDatabase").build()

    }
}