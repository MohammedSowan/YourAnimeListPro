package com.example.youranimelist2pro.objects

import androidx.room.Entity

data class Anime (
    val name:String? = "" ,
    val imageUrl: String? = "" ,
    val episodes: Int? = 0 ,
    val rating: Double? = 0.0 ,
    val description: String? = "" ,
    val genre: String? = ""
)