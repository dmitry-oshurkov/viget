package name.oshurkov.viget.youtube

import name.oshurkov.viget.io.exec

fun String.isYoutubeUrl() = startsWith("https://www.youtube.com/watch") || startsWith("https://youtu.be/")

fun execYoutubeDl(vararg args: String, progress: (String) -> Unit) = exec(
    "youtube-dl",
    *args,
    redirectStderr = true,
    progress = progress
)
