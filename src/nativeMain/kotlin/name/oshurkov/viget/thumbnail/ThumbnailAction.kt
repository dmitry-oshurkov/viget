package name.oshurkov.viget.thumbnail

import com.soywiz.korio.file.writeToFile
import io.ktor.client.*
import io.ktor.client.engine.curl.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import name.oshurkov.viget.thumbnailsDir
import org.gnome.gdkpixbuf.savev
import org.gnome.gtk.newPixbufFromFileAtScale
import platform.posix.remove

fun makeThumbnail(id: String, thumbnailUrl: String) {

    val tmpFile = "$thumbnailsDir/~$id.tmp"

    try {
        runBlocking {
            val bytes = client.get<ByteArray>(thumbnailUrl)
            bytes.writeToFile(tmpFile)
        }
        convertThumbnail(tmpFile, "$thumbnailsDir/$id.png")
    } finally {
        remove(tmpFile)
    }
}


private fun convertThumbnail(source: String, target: String) = newPixbufFromFileAtScale(source, 100, 100, 1).savev(target, "png", null, null)

private val client = HttpClient(Curl)
