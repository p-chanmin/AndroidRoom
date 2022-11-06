package kr.ac.tukorea.android.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.tukorea.android.room.databinding.ActivityDataBinding

class ItemActivity : AppCompatActivity() {

    private var mBinding: ActivityDataBinding? = null
    private val binding get() = mBinding!!

    private lateinit var db: ItemDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 싱글톤 데이터베이스 실행
        db = ItemDatabase.getInstance(applicationContext, viewModelStore)


    }

}