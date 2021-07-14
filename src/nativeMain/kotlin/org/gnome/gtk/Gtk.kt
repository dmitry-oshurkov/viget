package org.gnome.gtk

import interop.GtkTreeIter
import interop.gdk_pixbuf_new_from_file_at_scale
import interop.gtk_list_store_append
import interop.gtk_list_store_set
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import org.gnome.gdkpixbuf.Pixbuf

fun ListStore.append(iter: TreeIter) = gtk_list_store_append(this, iter.reinterpret())

fun ListStore.set(iter: TreeIter, value: Int) = gtk_list_store_set(this, iter, 0, value, -1)

fun newTreeIter() = memScoped {
    val iter = alloc<GtkTreeIter>()
    iter.ptr
}

fun newPixbufFromFileAtScale(
    filename: String,
    width: Int,
    height: Int,
    preserveAspectRatio: Int
): Pixbuf = gdk_pixbuf_new_from_file_at_scale(filename, width, height, preserveAspectRatio, null)!!.reinterpret()

val Widget.asImage: Image get() = reinterpret()
val Widget.asBox: Box get() = reinterpret()
val Widget.asLabel: Label get() = reinterpret()
val Widget.asGrid: Grid get() = reinterpret()
val Widget.asButton: Button get() = reinterpret()
