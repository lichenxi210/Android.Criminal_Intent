package com.example.criminal_intent.dao

import android.util.Log
import com.example.criminal_intent.model.Crime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.*

class CrimeRepository(private val crimeDao: CrimeDao) {
    suspend fun getAllCrimes(): List<Crime> {
        return crimeDao.getAllCrimes()
    }

    suspend fun getCrime(crimeId: UUID): Crime? {
        return crimeDao.getCrime(crimeId)
    }

    suspend fun addCrime(crime: Crime) {
        crimeDao.addCrime(crime)
        Log.d("CrimeRepository", "Crime added successfully: $crime")
    }

    suspend fun isDatabaseEmpty(): Boolean {
        return crimeDao.getAllCrimes().isEmpty()
    }

    suspend fun updateCrime(crime: Crime) {
        crimeDao.updateCrime(crime)
    }

    suspend fun clearAllCrimes() {
        crimeDao.clearAllCrimes()
    }
}