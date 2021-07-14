package name.oshurkov.viget.job

import kotlinx.serialization.Serializable
import name.oshurkov.viget.job.DownloadState.NEW

@Serializable
class Job(
    val url: String,
    val title: String,
    val duration: String? = null,
    val file: String? = null,
    val fileSize: Long? = null,
    val format: String? = null,
    val fps: Int? = null,
    var state: DownloadState = NEW,
)

enum class DownloadState { NEW, IN_PROGRESS, COMPLETED, ERROR }
