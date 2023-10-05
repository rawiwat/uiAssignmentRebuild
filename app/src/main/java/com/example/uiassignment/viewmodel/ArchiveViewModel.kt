package com.example.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.ArchiveScreenType
import com.example.uiassignment.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArchiveViewModel(database: Database, id:Int):ViewModel() {
    private val thisDatabase = database
    val model = thisDatabase.getModelFromID(id)
    val records = thisDatabase.getActivityRecords()
    private val _mode = MutableStateFlow(ArchiveScreenType.ACTIVITY)
    val mode:StateFlow<ArchiveScreenType> = _mode

    fun changeMode(type:ArchiveScreenType) {
        _mode.value = type
    }


}