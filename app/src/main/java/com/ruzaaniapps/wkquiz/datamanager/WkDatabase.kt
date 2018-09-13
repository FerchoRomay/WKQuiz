package com.ruzaaniapps.wkquiz.datamanager

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.content.Context
import com.ruzaaniapps.wkquiz.SingletonHolder

@Database(entities = [EntityUser::class, EntityKanji::class, EntityVocabulary::class],
        version = 1, exportSchema = false)
abstract class WkDatabase: RoomDatabase() {

    abstract fun wkDao(): WkDao

    companion object: SingletonHolder<WkDatabase, Context>({
        Room.databaseBuilder(it.applicationContext,
                WkDatabase::class.java, "wkdata.db")
                .build()
    })
}