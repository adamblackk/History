package com.adamblack.historyofempire.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.adamblack.historyofempire.model.FeedModel
import com.adamblack.historyofempire.model.RealmModel

@Dao
interface HistoryDao {
@Insert
suspend fun insertAll(vararg feedList:FeedModel):List<Long>


@Query("SELECT * FROM feedmodel")
suspend fun getAllList():List<FeedModel>

    @Query("DELETE FROM FeedModel")
    suspend fun deleteAllHistories()
}