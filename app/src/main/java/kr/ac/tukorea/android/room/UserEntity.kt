package kr.ac.tukorea.android.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userDB")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var index: Int,
    var name: String?,
    var age: Int?
)