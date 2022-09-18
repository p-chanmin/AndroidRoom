package kr.ac.tukorea.android.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface SmeltingDAO {

    @Insert(onConflict = REPLACE)
    fun insert(user: SmeltingEntity)

    @Query("SELECT * FROM testDB")
    fun getAll() : List<SmeltingEntity>


    @Delete
    fun delete(user: SmeltingEntity)

}