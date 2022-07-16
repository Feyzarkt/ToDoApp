package com.feyzaurkut.todoapp.widgets.medium

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.feyzaurkut.todoapp.R

class MediumDataProvider(
    private val context: Context,
    private val intent: Intent,
    private val titlesOfNotes: ArrayList<String>,
    private val descriptionsOfNotes: ArrayList<String>
) : RemoteViewsService.RemoteViewsFactory {

    override fun onCreate() {
        Log.e("onCreate", titlesOfNotes.toString())
        Log.e("onCreate", descriptionsOfNotes.toString())
    }

    override fun onDataSetChanged() {
    }

    override fun onDestroy() {}

    override fun getCount(): Int {
        return titlesOfNotes.size
    }

    override fun getViewAt(p0: Int): RemoteViews {
        val view = RemoteViews(context.packageName, R.layout.item_widget)
        view.setTextViewText(R.id.tvListItem1, titlesOfNotes[p0])
        view.setTextViewText(R.id.tvListItem2, descriptionsOfNotes[p0])
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
