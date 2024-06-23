package com.example.criminal_intent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.criminal_intent.dao.CrimeDao
import com.example.criminal_intent.model.Crime

@Database(entities = [Crime::class], version = 1)
@TypeConverters(CrimeTypeConverter::class)
abstract class CrimeDB : RoomDatabase() {
    abstract fun crimeDao(): CrimeDao
}