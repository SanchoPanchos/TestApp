package alex.com.testapp.data.source.local

import alex.com.testapp.data.model.City
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(City::class)], version = 1)
abstract class AppDB : RoomDatabase(){
    abstract fun citiesDAO() : CitiesDAO
}