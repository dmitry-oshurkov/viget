package name.oshurkov.viget.io

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.EOF
import platform.posix.SEEK_END
import platform.posix.SEEK_SET
import platform.posix.fclose
import platform.posix.fopen
import platform.posix.fputs
import platform.posix.fread
import platform.posix.fseek
import platform.posix.ftell

fun readAllText(filePath: String): String? {

    val file = fopen(filePath, "r")

    return when {

        file != null ->
            try {
                fseek(file, 0, SEEK_END)
                val fileSize = ftell(file)
                fseek(file, 0, SEEK_SET)

                memScoped {
                    val buffer = allocArray<ByteVar>(fileSize)
                    fread(buffer, fileSize.toULong(), 1, file) // read the entire file and store the contents into the buffer.
                    buffer.toKString()
                }
            } finally {
                fclose(file)
            }

        else -> null
    }
}

fun String.writeTo(filePath: String) {
    val file = fopen(filePath, "w") ?: throw IllegalArgumentException("Cannot open output file $filePath")
    try {
        memScoped {
            if (fputs(this@writeTo, file) == EOF)
                throw Error("File write error")
        }
    } finally {
        fclose(file)
    }
}
