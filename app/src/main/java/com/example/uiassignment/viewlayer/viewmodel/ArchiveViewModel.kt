package com.example.uiassignment.viewlayer.composeui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.miscellaneous.ArchiveScreenType
import com.example.uiassignment.data_layer.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArchiveViewModel(database: Database, id:Int):ViewModel() {
    val archivedTokens = database.getArchivedTokens()
    private val thisDatabase = database
    val model = thisDatabase.getModelFromID(id)
    val records = thisDatabase.getActivityRecords()
    val nfts = thisDatabase.getNFTs()
    private val _mode = MutableStateFlow(ArchiveScreenType.ACTIVITY)
    val mode:StateFlow<ArchiveScreenType> = _mode

    fun changeMode(type: ArchiveScreenType) {
        _mode.value = type
    }


}