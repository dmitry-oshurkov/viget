package name.oshurkov.viget.youtube

fun String.isYoutubeUrl() = startsWith("https://www.youtube.com/watch") || startsWith("https://youtu.be/")
