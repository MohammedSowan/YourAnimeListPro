package com.example.youranimelist2pro.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youranimelist2pro.database.DBAnimeDao
import com.example.youranimelist2pro.objects.DBAnime
import kotlinx.coroutines.launch

class DBAnimeViewModel(private  val dao: DBAnimeDao): ViewModel() {

    val dbAnimes = dao.getAllDBAnimes()

    fun insertAnime(dbAnime: DBAnime)=viewModelScope.launch {
        dao.insertDBAnime(dbAnime)
    }

    fun deleteAnime(dbAnime: DBAnime?)=viewModelScope.launch {
        if (dbAnime != null) {
            dao.deleteDBAnime(dbAnime)
        }
    }

    fun deleteAll() {
        dao.deleteAll()
    }
}
