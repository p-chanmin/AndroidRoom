# AndroidRoom
코틀린 Android Room DB



### 1. gradle

```kotlin
plugins {
   ...
}
apply plugin: 'kotlin-kapt'

...

dependencies {
	...

    // Room components
    def roomVersion = '2.2.5'
    implementation "androidx.room:room-runtime:$roomVersion"
    kapt "androidx.room:room-compiler:$roomVersion"
    implementation "androidx.room:room-ktx:$roomVersion"
    androidTestImplementation "androidx.room:room-testing:$roomVersion"

    // Kotlin components
    def coroutines = '1.3.4'
    def kotlin_version = "1.3.72"
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines"
}
```

kapt, Room, Coroutine



### 2. Entity

`UserEntity.kt`

```kotlin
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userDB")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) // PrimaryKey 를 자동적으로 생성
    var index: Int,
    var name: String,
    var age: Int
)
```



### 3. DAO

`UserDAO.kt`

```kotlin
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface UserDAO {

    @Insert(onConflict = REPLACE)
    fun insert(user: UserEntity)

    @Query("SELECT * FROM userDB")
    fun getAll() : List<UserEntity>

    // n살 이하인 데이터만 추출
    @Query("SELECT * FROM userDB WHERE age <= :n")
    fun upToAge(n: String): List<UserEntity>

    @Delete
    fun delete(user: UserEntity)

}
```



### 4. Database

`UserDatabase.kt`

```kotlin
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(UserEntity::class), version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun UserDAO() : UserDAO

    // 싱글톤 패턴
    companion object{
        var INSTANCE : UserDatabase? = null

        // Migration 코드
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("TEST","migrate 1 -> 2")
            }
        }

        fun getInstance(context: Context): UserDatabase? {
            if(INSTANCE == null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserDatabase::class.java, "userDB")
                        .createFromAsset("user.db")	// Assets 폴더에서 가져옴
                        .addMigrations()	// 버전 상관 없이 assets 폴더의 db를 우선으로 가져옴
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }

}
```



### 5. MainActivity

`MainActivity.kt`

```kotlin
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
```



### 6. 결과화면

|                   <b>Assets의 user.db</b>                    |                       <b>실행화면</b>                        |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src=https://user-images.githubusercontent.com/87304360/192141395-0837eb77-fb6d-4e89-bed5-013e65c60b56.PNG> | <img src=https://user-images.githubusercontent.com/87304360/192141401-35f8574b-f572-4b13-9384-de97800650d3.png> |
