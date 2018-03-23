package alex.com.testapp.data.source.remote

import alex.com.testapp.util.AppConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitManager {

    fun getApiService(): API {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val defaultParameters  = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("username", AppConstants.USERNAME)
                    .addQueryParameter("type", AppConstants.RESPONSE_TYPE)
                    .addQueryParameter("isNameRequired", AppConstants.IS_NAME_REQUIRED.toString())
                    .addQueryParameter("maxRows", AppConstants.MAX_ROWS.toString())
                    .build()

            val requestBuilder = original.newBuilder()
                    .url(url)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        val client = OkHttpClient.Builder()
                .addInterceptor(defaultParameters)
                .addInterceptor(logging)
                .build()
        return Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
    }
}