package alex.com.testapp.ui.archive

import alex.com.testapp.data.model.RequestCallback
import alex.com.testapp.data.source.remote.RequestManager
import alex.com.testapp.util.AppConstants
import alex.com.testapp.util.ZipUtil
import android.os.AsyncTask
import okhttp3.ResponseBody
import java.io.File


class ArchivePresenter(private val view : ArchiveContract.View) : ArchiveContract.Presenter {

    override fun downloadArchive(url: String, appFolder : File) {
        view.showView()
        RequestManager.downloadArchive(url, object : RequestCallback<ResponseBody>{
            override fun onDataLoaded(data: ResponseBody) {
                view.updateDialog("Unzipping archive...")
                saveAndUnzip(data, appFolder)
            }

            override fun onLoadFailed(error: String) {
                view.hideView()
                view.onError(error)
            }
        })
    }

    private fun saveAndUnzip(responseBody: ResponseBody, appFolder : File ){
        var files = listOf<File>()
        object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                ZipUtil.writeResponseBodyAsFile(responseBody, File(appFolder, AppConstants.ARCHIVE_NAME))
                files = ZipUtil.unpackZip(File(appFolder.absolutePath + AppConstants.ARCHIVE_NAME), File(appFolder.absolutePath + AppConstants.IMAGES_FOLDER))
            }

            override fun onPostExecute(result: Unit?) {
                view.hideView()
                view.onUnzipped(files)
            }
        }.execute()

    }
}