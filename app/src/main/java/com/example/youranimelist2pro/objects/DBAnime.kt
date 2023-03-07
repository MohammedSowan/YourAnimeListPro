package com.example.youranimelist2pro.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class DBAnime(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    val id:Int,
    @ColumnInfo(name = "anime_name") var name: String,
    @ColumnInfo(name = "anime_episodes") var episodes: String,
    @ColumnInfo(name = "anime_rating") var rating: String,
    @ColumnInfo(name = "anime_description") var description: String,
    @ColumnInfo(name = "anime_genre") var genre: String,
    @ColumnInfo(name = "anime_imgurl") var imgUri: String
)
