package com.example.youranimelist2pro.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.youranimelist2pro.objects.DBAnime

@Dao
interface DBAnimeDao {

    @Insert
    suspend fun insertDBAnime(dbAnime: DBAnime)

    @Delete
    suspend fun deleteDBAnime(dbAnime: DBAnime)

    @Query("SELECT * FROM anime_table")
    fun getAllDBAnimes(): LiveData<List<DBAnime>>

    @Query("SELECT * FROM anime_table")
    fun getAllUsers(): List<DBAnime?>?

    @Query("DELETE FROM anime_table")
    fun deleteAll()


}