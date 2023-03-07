package com.example.youranimelist2pro.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.youranimelist2pro.objects.DBAnime

@Database(entities = [DBAnime::class], version = 3, exportSchema = false)
abstract class AnimeDatabase: RoomDatabase() {
   // abstract val dbAnime: DBAnimeDao
    abstract fun dbAnime(): DBAnimeDao?

    companion object {
        @Volatile
        private var INSTANCE: AnimeDatabase? = null
        fun getInstance(context: Context): AnimeDatabase{
        synchronized(this){
            var instance = INSTANCE
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimeDatabase::class.java,
                    "anime_table"
                ).fallbackToDestructiveMigration()

                .build()
            }
            return instance
        }
        }
    }
}