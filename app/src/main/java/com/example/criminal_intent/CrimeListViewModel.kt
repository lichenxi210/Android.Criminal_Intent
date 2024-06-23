package com.example.criminal_intent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CrimeListViewModel : ViewModel() {
    private val _crimeListLiveData = MutableLiveData<List<Crime>>()
    val crimeListLiveData: LiveData<List<Crime>>
        get() = _crimeListLiveData

    init {
        val crimes = mutableListOf<Crime>()
        for (i in 0 until 100) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i % 2 == 0
            crimes += crime
        }
        _crimeListLiveData.value = crimes
    }
}
