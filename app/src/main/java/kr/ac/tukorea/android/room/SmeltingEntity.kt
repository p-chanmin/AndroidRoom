package kr.ac.tukorea.android.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "testDB")
data class SmeltingEntity(
    @PrimaryKey(autoGenerate = true)
    var index: Int,
    var Fuel: Int,
    var Stuff: Int,
    var Result: Int,
    var Name: String,
    var Type: String,
    var Furnace: Int,
    var Smoker: Int?,
    var Blaster: Int?
)