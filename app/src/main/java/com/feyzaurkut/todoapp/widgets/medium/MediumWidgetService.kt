package com.feyzaurkut.todoapp.widgets.medium

import android.content.Intent
import android.widget.RemoteViewsService

class MediumWidgetService: RemoteViewsService() {

    private lateinit var titlesOfNotes: ArrayList<String>
    private lateinit var descriptionsOfNotes: ArrayList<String>

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        titlesOfNotes = intent.getStringArrayListExtra("titlesOfNotes") as ArrayList<String>
        descriptionsOfNotes = intent.getStringArrayListExtra("descriptionsOfNotes") as ArrayList<String>
        return MediumDataProvider(this.applicationContext, intent, titlesOfNotes, descriptionsOfNotes)
    }
}