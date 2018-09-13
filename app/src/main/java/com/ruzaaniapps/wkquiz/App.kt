package com.ruzaaniapps.wkquiz

import android.app.Application
import android.arch.persistence.room.Room
import com.ruzaaniapps.wkquiz.datamanager.WkDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        datasource = Room.databaseBuilder(instance!!.applicationContext,
                WkDatabase::class.java, "wkdata.db").build()
    }

    companion object {
        var instance: App? = null
            private set
        var datasource: WkDatabase? = null
    }
}