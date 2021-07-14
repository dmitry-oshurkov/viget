package name.oshurkov.viget

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal inline fun <reified T> String.decodeTo() = json.decodeFromString<T>(this)


private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}
