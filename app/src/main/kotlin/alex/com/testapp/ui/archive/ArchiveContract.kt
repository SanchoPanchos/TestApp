package alex.com.testapp.ui.archive

import java.io.File

interface ArchiveContract {

    interface View{
        fun showView()
        fun hideView()
        fun onUnzipped(files : List<File>)
        fun onError(error : String)
        fun updateDialog(message :String)
    }

    interface Presenter{
        fun downloadArchive(url : String, appFolder : File)
    }
}