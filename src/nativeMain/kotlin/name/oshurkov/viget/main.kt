package name.oshurkov.viget

import binding.VigetUI
import org.gnome.gtk.ApplicationFactory
import org.gnome.gtk.addWindow
import org.gnome.gtk.asDialog
import org.gnome.gtk.asWidget
import org.gnome.gtk.hide
import org.gnome.gtk.onActivate
import org.gnome.gtk.onClicked
import org.gnome.gtk.run
import org.gnome.gtk.showAll
import org.gnome.gtk.text
import org.mrlem.gnome.gtk.newAndRun

fun main() = ApplicationFactory.newAndRun("name.oshurkov.viget") {

    VigetUI().apply {

        aboutButton.onClicked {
            aboutDialog.asDialog.run()
            aboutDialog.asWidget.hide()
        }
        euroAmountEntry.onActivate { showConverted() }
        convertButton.onClicked { showConverted() }

        mainWindow.asWidget.showAll()
        addWindow(mainWindow)
    }
}


private fun VigetUI.showConverted() {
    dollarAmountLabel.text = convert(euroAmountEntry.text?.toFloatOrNull())
}

private fun convert(euros: Float?) = euros
    ?.let { 123 }
    ?.let { "$ $it" }
    ?: "-"
