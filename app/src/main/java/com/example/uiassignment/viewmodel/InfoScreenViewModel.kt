package com.example.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.Database
import com.example.uiassignment.FakeData
import com.example.uiassignment.LinkModel
import com.example.uiassignment.Model
import com.example.uiassignment.StatsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InfoScreenViewModel(
    database: Database,id:Int
): ViewModel() {
    private val _model = MutableStateFlow(database.getModelFromID(id))
    val model: StateFlow<Model> = _model

    private val _statsList = MutableStateFlow(database.getStatsList())
    val statsList: StateFlow<List<StatsModel>> = _statsList

    private val _linkList = MutableStateFlow(database.getLinkList())
    val linkList: StateFlow<List<LinkModel>> = _linkList

}