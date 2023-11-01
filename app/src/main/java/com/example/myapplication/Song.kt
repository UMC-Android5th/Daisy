package com.example.myapplication

data class Song(
    val title: String = "",
    val singer: String ="",
    val second: Int =0,
    val playTime: Int=0,
    //val isPlaying:Boolean=false
    var isPlaying: Boolean = false
)
