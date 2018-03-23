package alex.com.testapp.ui.archive

import alex.com.testapp.R
import alex.com.testapp.data.model.GlideApp
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_image.view.*
import java.io.File


class ImageAdapter(private val images : List<File>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
       GlideApp.with(holder.itemView)
               .load(images[position])
               .into(holder.itemView.image)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
}