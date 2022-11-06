package kr.ac.tukorea.android.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemDB")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    var index: Int,
    var craft1: Int,
    var craft2: Int,
    var craft3: Int,
    var craft4: Int,
    var craft5: Int,
    var craft6: Int,
    var craft7: Int,
    var craft8: Int,
    var craft9: Int,
    var name: String,
    var result: Int,
    var type: String
)