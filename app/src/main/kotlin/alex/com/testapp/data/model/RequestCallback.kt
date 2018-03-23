package alex.com.testapp.data.model

interface RequestCallback<T> {

    fun onDataLoaded(data : T)
    fun onLoadFailed(error: String)
}