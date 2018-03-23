package alex.com.testapp.data.source.remote

import alex.com.testapp.data.model.GeonamesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import okhttp3.ResponseBody
import retrofit2.http.Streaming


interface API {

    @GET("/search")
    fun getCities(@Query("q") search : String) : Call<GeonamesResponse>

    @Streaming
    @GET
    fun downloadArchive(@Url url: String): Call<ResponseBody>
}