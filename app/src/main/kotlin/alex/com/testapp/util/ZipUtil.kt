package alex.com.testapp.util

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import okhttp3.ResponseBody


object ZipUtil {

    fun unpackZip(archivePath: File, filesPath : File) : List<File> {
        val files = arrayListOf<File>()
        val zis = ZipInputStream(BufferedInputStream(FileInputStream(archivePath)))
        try {
            var ze: ZipEntry
            var count: Int
            val buffer = ByteArray(8192)
            while (true) {
                val a = zis.nextEntry
                if(a == null)
                    break
                else
                    ze = a
                val file = File(filesPath, ze.name)
                files.add(file)
                val dir = if (ze.isDirectory) file else file.parentFile
                if (!dir.isDirectory && !dir.mkdirs())
                    throw FileNotFoundException("Failed to ensure directory: " + dir.getAbsolutePath())
                if (ze.isDirectory)
                    continue
                val fout = FileOutputStream(file)
                try {
                    while (true) {
                        count = zis.read(buffer)
                        if(count != -1)
                            fout.write(buffer, 0, count)
                        else
                            break
                    }
                } finally {
                    fout.close()
                }

            }
        } finally {
            zis.close()
        }
        return files
    }

    fun writeResponseBodyAsFile(body: ResponseBody, path : File): Boolean {
        try {
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(path)

                while (true) {
                    val read = inputStream!!.read(fileReader)
                    if (read == -1)
                        break
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                }
                outputStream.flush()
                return true
            } catch (e: IOException) {
                return false
            } finally {
                if (inputStream != null)
                    inputStream.close()
                if (outputStream != null)
                    outputStream.close()
            }
        } catch (e: IOException) {
            return false
        }

    }
}