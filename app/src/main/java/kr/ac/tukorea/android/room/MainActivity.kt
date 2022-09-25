package kr.ac.tukorea.android.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        // 싱글톤 데이터베이스 실행
        db = UserDatabase.getInstance(applicationContext)!!

        // 엔티티 추가
        binding.addBtn.setOnClickListener {
            var newUser = UserEntity(
                binding.editIndex.text.toString().toInt(),
                binding.editName.text.toString(),
                binding.editAge.text.toString().toInt()
                )
            addUser(newUser)
        }
        // 데이터베이스 불러오기
        binding.refreshBtn.setOnClickListener {
            refresh()
        }
        // 쿼리로 데이터베이스 불러오기
        binding.queryBtn.setOnClickListener {
            query(binding.queryAge.text.toString())
        }
        //데이터 전체 삭제
        binding.deleteBtn.setOnClickListener {
            delete()
        }



    }
    // 엔티티 추가
    fun addUser(user: UserEntity){
        CoroutineScope(Dispatchers.IO).launch {
            db.UserDAO().insert(user)
        }
    }
    // 데이터베이스 불러오기
    fun refresh(){
        var dbText = "결과\n"
        CoroutineScope(Dispatchers.IO).launch{
            val datas = CoroutineScope(Dispatchers.IO).async {
                db.UserDAO().getAll()
            }.await()
            for(data in datas){
                dbText += "index: ${data.index}, name: ${data.name}, age: ${data.age}\n"
            }
            runOnUiThread {
                binding.textDB.text = dbText
            }
        }
    }
    // 쿼리로 데이터베이스 불러오기
    fun query(n : String){
        var dbText = "결과\n"
        CoroutineScope(Dispatchers.IO).launch{
            val datas = CoroutineScope(Dispatchers.IO).async {
                db.UserDAO().upToAge(n)
            }.await()
            for(data in datas){
                dbText += "index: ${data.index}, name: ${data.name}, age: ${data.age}\n"
            }
            runOnUiThread {
                binding.textDB.text = dbText
            }
        }
    }
    //데이터 전체 삭제
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