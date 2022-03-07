package com.hegunhee.newsimplememoapp.daoTest

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Person::class],version = 1,exportSchema = false)
abstract class PersonDB(): RoomDatabase(){
    abstract fun dao() : PersonDao
}