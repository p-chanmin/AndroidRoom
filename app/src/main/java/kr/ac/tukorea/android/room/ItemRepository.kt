package kr.ac.tukorea.android.room

import androidx.lifecycle.LiveData

class ItemRepository(mDatabase: ItemDatabase?) {

    private val dao = mDatabase!!.ItemDAO()
    val allUsers: LiveData<List<ItemEntity>> = dao.getAll()
    companion object {
        private var sInstance: ItemRepository? = null
        fun getInstance(database: ItemDatabase): ItemRepository {
            return sInstance
                ?: synchronized(this) {
                    val instance = ItemRepository(database)
                    sInstance = instance
                    instance
                }
        }
    }

}