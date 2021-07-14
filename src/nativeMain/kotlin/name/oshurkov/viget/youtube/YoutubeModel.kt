package name.oshurkov.viget.youtube

import kotlinx.serialization.Serializable

@Serializable
class YoutubeVideo(
    val id: String,
    val title: String,
    val thumbnail: String,
)
