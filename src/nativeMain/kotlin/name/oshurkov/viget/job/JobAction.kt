package name.oshurkov.viget.job

import name.oshurkov.viget.decodeTo
import name.oshurkov.viget.encodeToJson
import name.oshurkov.viget.io.readAllText
import name.oshurkov.viget.io.writeTo
import name.oshurkov.viget.job.DownloadState.IN_PROGRESS
import name.oshurkov.viget.job.DownloadState.NEW
import name.oshurkov.viget.jobsFile
import name.oshurkov.viget.thumbnail.makeThumbnail
import name.oshurkov.viget.thumbnailsDir
import name.oshurkov.viget.youtube.YoutubeVideo
import name.oshurkov.viget.youtube.execYoutubeDl
import name.oshurkov.viget.youtube.isYoutubeUrl

fun placeToQueue(url: String?) = url
    ?.takeIf { it !in jobs.map { job -> job.url } }
    ?.takeIf { it.isYoutubeUrl() }
    ?.let { Job(url = it, title = it) }
    ?.also { jobs += it }

fun loadJobs() {
    jobs += readAllText(jobsFile)
        ?.decodeTo<List<Job>>()
        ?.onEach {
            if (it.state == IN_PROGRESS)
                it.state = NEW
        }
        .orEmpty()
}

fun Job.runDownload(onMakeThumbnail: (String) -> Unit) = run {

    execYoutubeDl("--dump-json", url) {

        val videoInfo = it.decodeTo<YoutubeVideo>()

        makeThumbnail(videoInfo.id, videoInfo.thumbnail)
        onMakeThumbnail("$thumbnailsDir/${videoInfo.id}.png")

        val maxQuality = false
        val height = if (maxQuality)
            "2160"
        else
            "1080"

        // tmp
        val outFile = "%(title)s.%(ext)s" // private val outFile = File(appConfig.downloadDir, "%(title)s.%(ext)s").absolutePath

        execYoutubeDl("--no-warnings", "-f", "\"bestvideo[height<=$height]+bestaudio/best[height<=$height]\"", "-o", "\"$outFile\"", url) {
        }
    }
}


/**
 * [jobs file](file://~/.local/share/viget/jobs.json)
 */
internal val jobs = ObservableList(mutableListOf<Job>()) {

    val save = { wrapped.encodeToJson().writeTo(jobsFile) }

    added(save)
    removed(save)
}
