package com.example.uiassignment.viewlayer.composeui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.data_layer.Database
import com.example.uiassignment.miscellaneous.GraphOutputType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InfoScreenViewModel(
    database: Database, id:Int
): ViewModel() {
    val model = database.getModelFromID(id)

    val statsList = database.getStatsList()

    val linkList = database.getLinkList()

    private var _graphType = MutableStateFlow(GraphOutputType.WEEK)
    val graphType: StateFlow<GraphOutputType> = _graphType

    fun changeGraphType(type: GraphOutputType) {
        _graphType.value = type
    }
}