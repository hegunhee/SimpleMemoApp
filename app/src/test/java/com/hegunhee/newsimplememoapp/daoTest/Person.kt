package com.hegunhee.newsimplememoapp.daoTest

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String
)