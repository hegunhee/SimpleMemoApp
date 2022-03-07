package com.hegunhee.newsimplememoapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hegunhee.newsimplememoapp.data.DB.provideDB
import com.hegunhee.newsimplememoapp.data.Entity.Memo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runBlocking(Dispatchers.IO) {
            val memo = Memo(
                1, "수입", 2022, 3, 6, "일", "오후", 8, 6, "용돈", 10000, "설명"
            )
            val dao = provideDB(this@MainActivity).dao()
            dao.addMemo(memo)
            val memos = dao.getAllMemo()
            Log.d("memo",memo.toString())
            Log.d("memoDao",memos[0].toString())
            dao.deleteMemo(memos[0])
        }
    }
}