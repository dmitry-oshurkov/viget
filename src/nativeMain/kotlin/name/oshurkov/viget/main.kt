package name.oshurkov.viget

import binding.VigetUI
import kotlinx.cinterop.reinterpret
import org.gnome.gtk.ApplicationFactory
import org.gnome.gtk.Grid
import org.gnome.gtk.addWindow
import org.gnome.gtk.asBox
import org.gnome.gtk.asButton
import org.gnome.gtk.asContainer
import org.gnome.gtk.asDialog
import org.gnome.gtk.asGrid
import org.gnome.gtk.asLabel
import org.gnome.gtk.asWidget
import org.gnome.gtk.children
import org.gnome.gtk.getChildAt
import org.gnome.gtk.hide
import org.gnome.gtk.insert
import org.gnome.gtk.onClicked
import org.gnome.gtk.run
import org.gnome.gtk.showAll
import org.gnome.gtk.text
import org.mrlem.gnome.gtk.get
import org.mrlem.gnome.gtk.newAndRun
import kotlin.system.getTimeMillis

fun main() = ApplicationFactory.newAndRun("name.oshurkov.viget") {

    VigetUI().apply {

        newButton.onClicked {

            val jobGridNew: Grid = builderNew["job_grid"].reinterpret()

            jobGridNew.apply {
                val box = getChildAt(1, 0)?.asBox
                val lbl = box?.asContainer?.children?.get(0)?.asLabel
                val grid = box?.asContainer?.children?.get(1)?.asGrid
                val btn1 = grid?.getChildAt(1, 1)?.asButton
                val btn2 = grid?.getChildAt(2, 1)?.asButton

                val txt = ">> ${getTimeMillis()}"

                lbl?.text = txt

                btn1?.onClicked {
                    println("b 1 $txt")
                }
                btn2?.onClicked {
                    println("b 2 $txt")
                }
            }

            jobListBox.insert(jobGridNew.asWidget, jobListBox.asContainer.children?.size ?: 0)
        }

        aboutMenuItem.asButton.onClicked {
            aboutDialog.asDialog.run()
            aboutDialog.asWidget.hide()
        }

        mainWindow.asWidget.showAll()
        addWindow(mainWindow)
    }
}


val userHome = getenv("HOME").toKString()
val thumbnailsDir = "$userHome/.local/share/viget/thumbnails"
