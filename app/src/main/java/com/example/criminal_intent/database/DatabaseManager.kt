package com.example.criminal_intent.database

import android.content.Context
import androidx.room.Room

object DatabaseManager {
    private var INSTANCE: CrimeDB? = null

    fun getDatabase(context: Context): CrimeDB {
        // 返回已存在的实例或者创建新的
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                CrimeDB::class.java,
                "crime_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}