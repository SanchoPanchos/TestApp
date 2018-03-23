package alex.com.testapp.util

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {

    fun isNetworkEnabled(context : Context) : Boolean{
        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo != null
    }
}