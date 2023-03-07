package com.example.youranimelist2pro.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class DBAnimeViewModelFactory (
    private val dao: DBAnimeDao
    ): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DBAnimeViewModel::class.java)) {
            return DBAnimeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

    }

