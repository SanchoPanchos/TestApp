package alex.com.testapp.ui.city

import alex.com.testapp.data.model.City

interface CitiesContract {

    interface View{
        fun onCitiesLoaded(citiesLoaded: List<City>)
        fun onLoadFailed(error: String)
        fun onLoadedFromDB(cities: List<City>)
        fun onCitySaved(city: City)
        fun onCityDeleted()
    }

    interface Presenter{
        fun loadCities(search : String)
        fun loadFromDB()
        fun saveToDB(city: City)
        fun deleteFromDB(city: City)
    }
}