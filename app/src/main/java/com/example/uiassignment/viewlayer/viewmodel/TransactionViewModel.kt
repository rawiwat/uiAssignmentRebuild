package com.example.uiassignment.viewlayer.composeui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.data_layer.Database
import com.example.uiassignment.miscellaneous.Model
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TransactionViewModel(database: Database, id: Int):ViewModel() {
    private val thisDatabase = database
    private val _model = MutableStateFlow(thisDatabase.getModelFromID(id))
    val model: StateFlow<Model> = _model
    val listOfModels = thisDatabase.getModels()
    val tokens = thisDatabase.getTokens()
    fun changeModel(id: Int) {
        _model.value = thisDatabase.getModelFromID(id)
    }
}