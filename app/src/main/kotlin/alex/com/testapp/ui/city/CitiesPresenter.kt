package alex.com.testapp.ui.city

import alex.com.testapp.App
import alex.com.testapp.data.model.City
import alex.com.testapp.data.model.RequestCallback
import alex.com.testapp.data.source.remote.RequestManager
import android.os.AsyncTask

class CitiesPresenter(val view : CitiesContract.View) : CitiesContract.Presenter {

    override fun loadCities(search: String) {
        RequestManager.getCities(search, object : RequestCallback<List<City>>{
            override fun onDataLoaded(data: List<City>) {
                view.onCitiesLoaded(data)
            }

            override fun onLoadFailed(error: String) {
                view.onLoadFailed(error)
            }
        })
    }

    override fun loadFromDB() {
        var list = listOf<City>()
        object : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                list = App.appInstance.appDB.citiesDAO().getAll()
            }

            override fun onPostExecute(result: Unit?) {
                    view.onLoadedFromDB(list)
            }
        }.execute()
    }

    override fun saveToDB(city: City) {
        object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                App.appInstance.appDB.citiesDAO().insert(city)
            }

            override fun onPostExecute(result: Unit?) {
                view.onCitySaved(city)
            }
        }.execute()
    }

    override fun deleteFromDB(city: City){
        object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                App.appInstance.appDB.citiesDAO().delete(city)
            }

            override fun onPostExecute(result: Unit?) {
                view.onCityDeleted()
            }
        }.execute()
    }
}