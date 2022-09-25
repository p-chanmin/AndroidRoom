package kr.ac.tukorea.android.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface UserDAO {

    @Insert(onConflict = REPLACE)
    fun insert(user: UserEntity)

    @Query("SELECT * FROM userDB")
    fun getAll() : List<UserEntity>

    @Query("SELECT * FROM userDB WHERE age <= :n")
    fun upToAge(n: String): List<UserEntity>

    @Delete
    fun delete(user: UserEntity)

}