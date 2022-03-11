package com.hegunhee.newsimplememoapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hegunhee.newsimplememoapp.data.DB.provideDB
import com.hegunhee.newsimplememoapp.data.Entity.Memo
import com.hegunhee.newsimplememoapp.data.Entity.getTwentyMockingMemo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runBlocking(Dispatchers.IO) {
            val memos : List<Memo> = getTwentyMockingMemo().filter { it.year == 2022 }.filter{it.month == 3}.sortedByDescending { it.day }

            val dao = provideDB(this@MainActivity).dao()
            val memoss = getTwentyMockingMemo()
            dao.insertAllMemo(*memoss.toTypedArray())
            val daoMemos = dao.getMemoSortedByYearAndMonth(2022,12)

            Log.d("memoOne",dao.getMemo(1).toString())
            Log.d("memo",memos.toString())
            Log.d("memoDao",daoMemos.toString())
            Log.d("memoTest",dao.getAllMemo().toString())
        }
    }
}