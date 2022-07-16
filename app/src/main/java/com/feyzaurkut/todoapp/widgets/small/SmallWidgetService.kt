package com.feyzaurkut.todoapp.widgets.small

import android.content.Intent
import android.widget.RemoteViewsService

class SmallWidgetService: RemoteViewsService() {

    private lateinit var titlesOfNotes: ArrayList<String>

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        titlesOfNotes = intent.getStringArrayListExtra("titlesOfNotes") as ArrayList<String>
        return SmallDataProvider(this.applicationContext, intent, titlesOfNotes)
    }
}