package kr.ac.tukorea.android.room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.tukorea.android.room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private lateinit var db: ItemDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 싱글톤 데이터베이스 실행
        db = ItemDatabase.getInstance(applicationContext)!!

        binding.nextPage.setOnClickListener {
            startActivity(Intent(this, ItemActivity::class.java))
        }

    }

}