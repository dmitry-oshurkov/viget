package name.oshurkov.viget

import binding.VigetUI
import org.gnome.gtk.ApplicationFactory
import org.gnome.gtk.addWindow
import org.gnome.gtk.append
import org.gnome.gtk.asButton
import org.gnome.gtk.asDialog
import org.gnome.gtk.asWidget
import org.gnome.gtk.hide
import org.gnome.gtk.newTreeIter
import org.gnome.gtk.onClicked
import org.gnome.gtk.run
import org.gnome.gtk.set
import org.gnome.gtk.showAll
import org.mrlem.gnome.gtk.newAndRun

fun main() = ApplicationFactory.newAndRun("name.oshurkov.viget") {

    VigetUI().apply {

        newButton.onClicked {
            newTreeIter().apply {
                jobList.append(this)
                jobList.set(this, 88)
            }
        }

        aboutMenuItem.asButton.onClicked {
            aboutDialog.asDialog.run()
            aboutDialog.asWidget.hide()
        }

        mainWindow.asWidget.showAll()
        addWindow(mainWindow)
    }
}
