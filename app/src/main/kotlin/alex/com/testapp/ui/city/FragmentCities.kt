package alex.com.testapp.ui.city

import alex.com.testapp.R
import alex.com.testapp.data.model.City
import alex.com.testapp.data.model.ListCallback
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_cities.*
import kotlinx.android.synthetic.main.fragment_cities.view.*

class FragmentCities : Fragment(), CitiesContract.View{

    private var lastEdited: Long = 0
    private val DELAY: Long = 1000
    private val handler = Handler()

    private var citiesLoaded = listOf<City>()
    private var citiesDB = arrayListOf<City>()
    private var position = 0

    private lateinit var adapter: CityAdapter
    private lateinit var presenter: CitiesContract.Presenter
    private lateinit var dialog : AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cities, container, false)
        presenter = CitiesPresenter(this)
        presenter.loadFromDB()
        initDialog()
        initAutoComplete(view)
        initList(view)
        return view
    }

    private fun initList(view: View) {
        adapter = CityAdapter(citiesDB, object : ListCallback{
            override fun onLongTouch(pos: Int) {
                position = pos
                dialog.show()
            }
        })
        view.citiesRecycler.adapter = adapter
        view.citiesRecycler.layoutManager = LinearLayoutManager(activity)
    }

    private fun initAutoComplete(view: View) {
        view.autoComplete.setOnItemClickListener { _, _, position, _ ->
            presenter.saveToDB(citiesLoaded[position])
            autoComplete.setText("")
        }
        view.autoComplete.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length > 2) {
                    lastEdited = System.currentTimeMillis()
                    handler.postDelayed({
                        if (System.currentTimeMillis() > (lastEdited + DELAY - 100)) {
                            presenter.loadCities(s.toString())
                        }
                    }, DELAY)
                } else {

                }
            }
        })
    }

    private fun initDialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setItems(arrayOf("Delete"), { _,_ ->
            presenter.deleteFromDB(citiesDB[position])
        })
        dialog = builder.create()
    }

    override fun onCitiesLoaded(citiesLoaded: List<City>) {
        this.citiesLoaded = citiesLoaded
        val cityNames = arrayListOf<String>()
        for(cityLoaded in citiesLoaded){
            if(!citiesDB.contains(cityLoaded))
                cityNames.add(cityLoaded.name)
        }
        autoComplete.setAdapter(ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line,
                cityNames))
        autoComplete.showDropDown()
    }

    override fun onLoadedFromDB(cities: List<City>) {
        citiesDB.addAll(cities)
        adapter.notifyDataSetChanged()
    }

    override fun onLoadFailed(error: String) {
        //TODO handle error
    }

    override fun onCityDeleted() {
        citiesDB.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    override fun onCitySaved(city: City) {
        citiesDB.add(0, city)
        adapter.notifyDataSetChanged()
    }
}
