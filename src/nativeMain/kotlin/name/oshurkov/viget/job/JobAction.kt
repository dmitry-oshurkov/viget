package name.oshurkov.viget.job

import name.oshurkov.viget.decodeTo
import name.oshurkov.viget.io.readAllText
import name.oshurkov.viget.job.DownloadState.IN_PROGRESS
import name.oshurkov.viget.job.DownloadState.NEW
import name.oshurkov.viget.jobsFile
import name.oshurkov.viget.youtube.isYoutubeUrl

fun placeToQueue(url: String?) = url
    ?.takeIf { it !in jobs.map { job -> job.url } }
    ?.takeIf { it.isYoutubeUrl() }
    ?.let { Job(url = it, title = it) }
    ?.also {
        jobs += it
    }

fun loadJobs() {
    jobs += readAllText(jobsFile)
        ?.decodeTo<List<Job>>()
        ?.onEach {
            if (it.state == IN_PROGRESS)
                it.state = NEW
        }
        .orEmpty()
}


/**
 * [jobs file](file://~/.local/share/viget/jobs.json)
 */
internal val jobs = mutableListOf<Job>()
