package alex.com.testapp.ui.city

import alex.com.testapp.R
import alex.com.testapp.data.model.City
import alex.com.testapp.data.model.ListCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_city.view.*


class CityAdapter(private val cities : List<City>, private val callback : ListCallback) :
        RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_city, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = cities.size

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.itemView.setOnLongClickListener({
            callback.onLongTouch(position)
            true
        })
        holder.itemView.name.text = (cities[position].name + ", " + cities[position].geocode)
        holder.itemView.population.text = cities[position].population.toString()
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
}