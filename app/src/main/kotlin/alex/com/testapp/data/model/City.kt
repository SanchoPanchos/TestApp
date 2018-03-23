package alex.com.testapp.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class City {

    @PrimaryKey
    @SerializedName("geonameId")
    var id = 0

    var population = 0
    var geocode = ""
    var name = ""

    override fun equals(other: Any?): Boolean {
        return if(other !is City)
            false
        else
            other.id == id
    }
}
