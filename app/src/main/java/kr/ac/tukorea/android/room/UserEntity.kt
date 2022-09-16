package kr.ac.tukorea.android.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var age: String,
    var phone: String
)