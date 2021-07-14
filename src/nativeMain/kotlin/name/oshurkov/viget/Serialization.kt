package name.oshurkov.viget

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal inline fun <reified T> String.decodeTo() = json.decodeFromString<T>(this)
internal inline fun <reified T> T.encodeToJson() = json.encodeToString(this)


private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}
