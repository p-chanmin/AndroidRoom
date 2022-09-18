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
import java.nio.file.Files.delete

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private lateinit var db: SmeltingDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = SmeltingDatabase.getInstance(applicationContext)!!

        binding.refreshBtn.setOnClickListener {
            refresh()
        }
//        binding.deleteBtn.setOnClickListener {
//            delete()
//        }

        binding.lang.text = getString(R.string.hello)


    }

//    fun addUser(user: UserEntity){
//        CoroutineScope(Dispatchers.IO).launch {
//            db.UserDAO().insert(user)
//        }
//    }

    fun refresh(){
        var dbText = "결과\n"
        CoroutineScope(Dispatchers.IO).launch{
            val datas = CoroutineScope(Dispatchers.IO).async {
                db.SmeltingDAO().getAll()
            }.await()
            Log.d("TEST", "TEST :: REFRESH")
            for(data in datas){
                Log.d("TEST", ":: CALL")
                dbText += "name: ${data.Name}, type: ${data.Type}, furnace: ${data.Furnace}, smoker: ${data.Smoker}, blaster: ${data.Blaster}\n"
            }
            runOnUiThread {
                binding.showDB.text = dbText
            }
        }
    }
//    fun delete(){
//        CoroutineScope(Dispatchers.IO).launch{
//            val users = CoroutineScope(Dispatchers.IO).async {
//                db.UserDAO().getAll()
//            }.await()
//            for(user in users){
//                db.UserDAO().delete(user)
//            }
//        }
//
//    }

}