package com.example.criminal_intent.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.criminal_intent.model.Crime
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface CrimeDao {
    @Query("SELECT * FROM Crime")
    suspend fun getAllCrimes(): List<Crime>

    @Query("SELECT * FROM Crime WHERE id = :crimeId")
    suspend fun getCrime(crimeId: UUID): Crime?

    @Insert
    suspend fun addCrime(crime: Crime)

    @Update
    suspend fun updateCrime(crime: Crime)

    @Query("DELETE FROM Crime")
    suspend fun clearAllCrimes()
}