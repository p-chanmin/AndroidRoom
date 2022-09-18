package kr.ac.tukorea.android.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(SmeltingEntity::class), version = 1)
abstract class SmeltingDatabase : RoomDatabase() {
    abstract fun SmeltingDAO() : SmeltingDAO

    // 싱글톤 패턴
    companion object{
        var INSTANCE : SmeltingDatabase? = null

        // Migration 코드
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("TEST","migrate 1 -> 2")
            }
        }

        fun getInstance(context: Context): SmeltingDatabase? {
            if(INSTANCE == null){
                synchronized(SmeltingDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        SmeltingDatabase::class.java, "smelting.db")
                        .createFromAsset("test.db")
                        .addMigrations()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }

}