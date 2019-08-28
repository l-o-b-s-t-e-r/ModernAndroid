package com.example.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.data.dao.UserDao
import com.example.myapplication.domain.entities.UserEntity

@Database(entities = arrayOf(UserEntity::class), version = 2)
@TypeConverters(value = [DateTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object Migrations {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE user ADD COLUMN color INTEGER NOT NULL DEFAULT(-1)")
            }
        }
    }
}