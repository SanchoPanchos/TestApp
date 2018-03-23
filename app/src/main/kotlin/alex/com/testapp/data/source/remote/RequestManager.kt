package alex.com.testapp.data.source.remote

import alex.com.testapp.data.model.City
import alex.com.testapp.data.model.GeonamesResponse
import alex.com.testapp.data.model.RequestCallback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RequestManager {

    private val apiService : API = RetrofitManager.getApiService()

    fun getCities(search : String, callback : RequestCallback<List<City>>){
        apiService.getCities(search).enqueue(object : Callback<GeonamesResponse>{
            override fun onFailure(call: Call<GeonamesResponse>?, t: Throwable?) {
                callback.onLoadFailed(t?.message!!)
            }

            override fun onResponse(call: Call<GeonamesResponse>?, response: Response<GeonamesResponse>) {
                if (response.isSuccessful){
                    callback.onDataLoaded(response.body()?.geonames!!)
                }
                else
                    callback.onLoadFailed(response.message())
            }
        })

    }

    fun downloadArchive(url :String, callback : RequestCallback<ResponseBody>){
        apiService.downloadArchive(url).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                callback.onLoadFailed(t?.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if(response?.body() == null)
                    callback.onLoadFailed("Error loading archive")
                else if(response.isSuccessful)
                    callback.onDataLoaded(response.body()!!)
                else
                    callback.onLoadFailed(response.message())
            }
        })
    }


}