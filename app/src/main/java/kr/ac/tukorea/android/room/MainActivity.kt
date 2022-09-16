package kr.ac.tukorea.android.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kr.ac.tukorea.android.room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user.db"
        ).build()

        binding.submitBtn.setOnClickListener {
            var newUser = UserEntity(
                binding.textId.text.toString().toInt(),
                binding.textName.text.toString(),
                binding.textAge.text.toString(),
                binding.textPhone.text.toString()
            )
            addUser(newUser)
        }
        binding.refreshBtn.setOnClickListener {
            refresh()
        }
        binding.deleteBtn.setOnClickListener {
            delete()
        }



    }

    fun addUser(user: UserEntity){
        CoroutineScope(Dispatchers.IO).launch {
            db.UserDAO().insert(user)
        }
    }
    fun refresh(){
        var dbText = "유저 리스트\n"
        CoroutineScope(Dispatchers.IO).launch{
            val users = CoroutineScope(Dispatchers.IO).async {
                db.UserDAO().getAll()
            }.await()

            for(user in users){
                dbText += "id: ${user.id}, name: ${user.name}, age: ${user.age}, phone: ${user.phone}\n"
            }
            runOnUiThread {
                binding.showDB.text = dbText
            }
        }
    }
    fun delete(){
        CoroutineScope(Dispatchers.IO).launch{
            val users = CoroutineScope(Dispatchers.IO).async {
                db.UserDAO().getAll()
            }.await()
            for(user in users){
                db.UserDAO().delete(user)
            }
        }

    }

}