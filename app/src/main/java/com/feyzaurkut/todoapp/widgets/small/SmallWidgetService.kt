package com.feyzaurkut.todoapp.widgets

import android.content.Intent
import android.widget.RemoteViewsService

class WidgetService: RemoteViewsService() {

    private lateinit var titlesOfNotes: ArrayList<String>

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        titlesOfNotes = intent.getStringArrayListExtra("titlesOfNotes") as ArrayList<String>
        return DataProvider(this.applicationContext, intent, titlesOfNotes)
    }
}