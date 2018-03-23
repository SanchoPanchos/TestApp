package alex.com.testapp.ui.archive

import alex.com.testapp.R
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_archive.*
import kotlinx.android.synthetic.main.fragment_archive.view.*
import java.io.File


class FragmentArchive : Fragment(), ArchiveContract.View {

    private lateinit var presenter: ArchiveContract.Presenter
    private lateinit var dialog : ProgressDialog


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_archive, container, false)
        presenter = ArchivePresenter(this)
        initDialog()
        view.load.setOnClickListener {
            loadImages()
        }
        return view
    }

    private fun initDialog() {
        dialog = ProgressDialog(activity)
        dialog.setTitle("Please wait")
        dialog.setMessage("Loading archive...")
        dialog.setCancelable(false)
    }

    private fun loadImages() {
        if(link.text.isEmpty())
            Toast.makeText(activity, "Empty link", Toast.LENGTH_SHORT).show()
        else
            presenter.downloadArchive(link.text.toString(), activity!!.getExternalFilesDir(""))
    }

    override fun showView() {
        dialog.show()
    }

    override fun hideView() {
        dialog.hide()
    }

    override fun onUnzipped(files : List<File>) {
        val adapter = ImageAdapter(files)
        imagesRecycler.adapter = adapter
        imagesRecycler.layoutManager = LinearLayoutManager(activity)
    }

    override fun onError(error: String) {
    }

    override fun updateDialog(message: String) {
        dialog.setMessage(message)
    }
}
