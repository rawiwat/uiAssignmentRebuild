package com.example.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.Database
import kotlinx.coroutines.flow.MutableStateFlow

class ArchiveViewModel(database: Database, id:Int):ViewModel() {
    private val thisDatabase = database
    val model = thisDatabase.getModelFromID(id)
    val records = thisDatabase.getActivityRecords()
}