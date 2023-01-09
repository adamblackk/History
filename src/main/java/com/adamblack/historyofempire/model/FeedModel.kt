package com.adamblack.historyofempire.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FeedModel(
    @ColumnInfo(name = "title")
    val title:String,
    @ColumnInfo(name = "brief")
    val brief:String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl:String,
    @ColumnInfo(name = "sınıf")
    val sınıf:String,
    @ColumnInfo(name = "text")
    val text:String,
    @ColumnInfo(name = "resourceUrl")
    val resourceUrl:String,
    @ColumnInfo(name = "id")
    val id:String
){
    @PrimaryKey(autoGenerate = true)
    var roomId=0
}
