package kr.ac.tukorea.android.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(UserEntity::class), version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun UserDAO() : UserDAO

    // 싱글톤 패턴
    companion object{
        var INSTANCE : UserDatabase? = null

        // Migration 코드
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("TEST","migrate 1 -> 2")
            }
        }

        fun getInstance(context: Context): UserDatabase? {
            if(INSTANCE == null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserDatabase::class.java, "userDB")
                        .createFromAsset("user.db")
                        .addMigrations()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }

}