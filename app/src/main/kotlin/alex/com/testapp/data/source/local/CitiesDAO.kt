package alex.com.testapp.data.source.local

import alex.com.testapp.data.model.City
import android.arch.persistence.room.*
import android.arch.persistence.room.Delete



@Dao
interface CitiesDAO {

    @Query("SELECT * FROM City")
    fun getAll(): List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City)

    @Delete
    fun delete(city: City)

}