package kr.ac.tukorea.android.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface UserDAO {

    @Insert(onConflict = REPLACE)
    fun insert(user: UserEntity)

    @Query("SELECT * FROM user")
    fun getAll() : List<UserEntity>


    @Delete
    fun delete(user: UserEntity)

}