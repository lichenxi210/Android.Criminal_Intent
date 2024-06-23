package com.example.criminal_intent.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.criminal_intent.dao.CrimeRepository

class CrimeListViewModelFactory(private val repository: CrimeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CrimeListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CrimeListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}