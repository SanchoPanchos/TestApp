package alex.com.testapp

import alex.com.testapp.data.source.local.AppDB
import android.app.Application
import android.arch.persistence.room.Room


class App : Application(){

    lateinit var appDB: AppDB

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        appDB = Room.databaseBuilder(this, AppDB::class.java, "database")
                .build()
    }

    companion object {
        lateinit var appInstance : App
    }

}