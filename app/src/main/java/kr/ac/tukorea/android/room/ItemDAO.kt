package kr.ac.tukorea.android.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDAO {

    @Query("SELECT * FROM itemDB")
    fun getAll() : LiveData<List<ItemEntity>>

    @Query("SELECT * FROM itemDB WHERE type == :typeName")
    fun getFromType(typeName: String): LiveData<List<ItemEntity>>

}