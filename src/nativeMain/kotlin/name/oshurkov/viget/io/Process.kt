package name.oshurkov.viget.io

import kotlinx.cinterop.refTo
import kotlinx.cinterop.toKString
import platform.posix.fgets
import platform.posix.pclose
import platform.posix.popen

fun exec(exe: String, vararg args: String, redirectStderr: Boolean, progress: (String) -> Unit) {

    val command = (listOf(exe) + args).joinToString(" ")
    val commandToExecute = if (redirectStderr)
        "$command 2>&1"
    else
        command

    val fp = popen(commandToExecute, "r")

    if (fp != null) {

        val stdout = buildString {
            val buffer = ByteArray(4096)
            while (true) {
                val input = fgets(buffer.refTo(0), buffer.size, fp) ?: break
                val value = input.toKString()
                append(value)
            }
        }

        val status = pclose(fp)
        if (status != 0) {
            val message = "Command `$command` failed with status $status${if (redirectStderr) ": $stdout" else ""}"
            println(message)
            error(message)
        }

        progress(stdout)
    }
    else
        error("Failed to run command: $command")
}
