package kr.ac.tukorea.android.room

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModelStore
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(ItemEntity::class), version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun ItemDAO() : ItemDAO

    // 싱글톤 패턴
    companion object{
        var INSTANCE : ItemDatabase? = null

        // Migration 코드
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("TEST","migrate 1 -> 2")
            }
        }

        fun getInstance(context: Context, scope: ViewModelStore): ItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java, "ItemDB")
                    .addMigrations()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}