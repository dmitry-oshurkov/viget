package org.gnome.gtk

import interop.GtkTreeIter
import interop.gtk_list_store_append
import interop.gtk_list_store_set
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret

fun ListStore.append(iter: TreeIter) = gtk_list_store_append(this, iter.reinterpret())

fun ListStore.set(iter: TreeIter, value: Int) = gtk_list_store_set(this, iter, 0, value, -1)

fun newTreeIter() = memScoped {
    val iter = alloc<GtkTreeIter>()
    iter.ptr
}
