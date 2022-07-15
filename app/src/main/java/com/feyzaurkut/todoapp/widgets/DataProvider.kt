package com.feyzaurkut.todoapp.widgets

import android.R.id.text1
import android.R.layout.simple_list_item_1
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class DataProvider(
    private val context: Context,
    private val intent: Intent,
    private val titlesOfNotes: ArrayList<String>
) : RemoteViewsService.RemoteViewsFactory {

    override fun onCreate() {
    }

    override fun onDataSetChanged() {
    }

    override fun onDestroy() {}

    override fun getCount(): Int {
        return titlesOfNotes.size
    }

    override fun getViewAt(p0: Int): RemoteViews {
        val view = RemoteViews(context.packageName, simple_list_item_1)
        view.setTextViewText(text1, titlesOfNotes[p0])
        return view
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }


}
