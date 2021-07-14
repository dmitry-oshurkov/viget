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

class ObservableList<T>(val wrapped: MutableList<T>, init: ObservableList<T>.() -> Unit) : MutableList<T> by wrapped {

    init {
        init(this)
    }

    private var onAdd: (() -> Unit)? = null
    private var onRemove: (() -> Unit)? = null

    fun added(fn: () -> Unit) {
        onAdd = fn
    }

    fun removed(fn: () -> Unit) {
        onRemove = fn
    }

    override fun add(element: T) = wrapped.add(element).also { if (it) onAdd?.invoke() }

    override fun remove(element: T) = wrapped.remove(element).also { if (it) onRemove?.invoke() }
}
