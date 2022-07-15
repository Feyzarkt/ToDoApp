package com.feyzaurkut.todoapp.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.feyzaurkut.todoapp.R
import com.feyzaurkut.todoapp.data.model.RequestState
import com.feyzaurkut.todoapp.domain.usecase.GetNotesUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmallWidget : AppWidgetProvider() {

    @Inject
    lateinit var getNotesUseCase: GetNotesUseCase
    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)
    private var titlesOfNotes: ArrayList<String> = arrayListOf()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        coroutineScope.launch {
            val views = RemoteViews(context.packageName, R.layout.small_widget)
            getNotesUseCase.invoke().collect {
                when (it) {
                    is RequestState.Success -> {
                        it.data.forEach { note ->
                            note.title?.let { it1 -> titlesOfNotes.add(it1) }
                        }
                        Log.e("getNotes", it.data.toString())
                        setRemoteAdapter(context, views)
                    }
                    is RequestState.Error -> {
                        Log.e("Error", it.exception.toString())
                    }
                    is RequestState.Loading -> {
                        Log.e("Loading", "Loading")
                    }
                }
            }
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun setRemoteAdapter(context: Context, views: RemoteViews) {
        val intent = Intent(context, WidgetService::class.java)
        intent.putExtra("titlesOfNotes", titlesOfNotes)
        views.setRemoteAdapter(R.id.lvNotes, intent)
    }

}