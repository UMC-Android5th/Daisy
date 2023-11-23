package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.ArrayList

//@Entity(tableName = "AlbumTable")
//data class Album(
//    @PrimaryKey(autoGenerate = false) var id: Int = 0,
//    var title: String? = "",
//    var singer: String? = "",
//    var coverImg: Int? = null
//)


data class Album (
    val title : String? = "",
    val singer : String? = "",
    val coverImage: Int? = null,
    val song: ArrayList<Song>? = null
)