package kr.ac.tukorea.android.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    val Repository: ItemRepository =
        ItemRepository(ItemDatabase.getInstance(application, viewModelScope))

    var allUsers: LiveData<List<ItemEntity>> = Repository.allUsers

    fun getAll(): LiveData<List<ItemEntity>>{
        return allUsers
    }

}