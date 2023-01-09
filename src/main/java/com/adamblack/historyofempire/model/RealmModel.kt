package com.adamblack.historyofempire.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.realm.RealmObject
import io.realm.annotations.RealmClass
@Entity
@RealmClass
open class RealmModel() :RealmObject(){
    @ColumnInfo(name = "title")
    var title:String?=null
    @ColumnInfo(name = "brief")
    var brief:String?=null
    @ColumnInfo(name = "imageUrl")
    var imageUrl:String?=null
    @ColumnInfo(name = "sınıf")
    var sınıf:String?=null
    @ColumnInfo(name = "text")
    var text:String?=null
    @ColumnInfo(name = "resourceUrl")
    var resourceUrl:String?=null
    @ColumnInfo(name = "Id")
    var Id:Int?=null
    @ColumnInfo(name = "anaSınıf")
    var anaSınıf:String?=null
    @ColumnInfo(name = "like")
    var like:Boolean?=false
    @ColumnInfo(name = "bookMark")
    var bookMark:Boolean?=false

    //Room için bunu karıştırma
    @PrimaryKey(autoGenerate = true)
    var idd:Int=0
}