package name.oshurkov.viget

import binding.VigetUI
import interop.g_mkdir_with_parents
import name.oshurkov.viget.job.loadJobs
import org.gnome.gtk.ApplicationFactory
import org.gnome.gtk.addWindow
import org.gnome.gtk.asButton
import org.gnome.gtk.asDialog
import org.gnome.gtk.asWidget
import org.gnome.gtk.hide
import org.gnome.gtk.onClicked
import org.gnome.gtk.run
import org.gnome.gtk.showAll
import org.mrlem.gnome.gtk.newAndRun
import org.mrlem.gnome.toKString
import platform.posix.getenv

fun main() = ApplicationFactory.newAndRun("name.oshurkov.viget") {

    g_mkdir_with_parents(thumbnailsDir, 493)
    loadJobs()

    VigetUI().apply {

        newButton.onClicked {
        }

        aboutMenuItem.asButton.onClicked {
            aboutDialog.asDialog.run()
            aboutDialog.asWidget.hide()
        }

        mainWindow.asWidget.showAll()
        addWindow(mainWindow)
    }
}


// todo check visibility
val userHome = getenv("HOME").toKString()
val dataDir = "$userHome/.local/share/viget"
val thumbnailsDir = "$dataDir/thumbnails"
val jobsFile = "$dataDir/jobs.json"
