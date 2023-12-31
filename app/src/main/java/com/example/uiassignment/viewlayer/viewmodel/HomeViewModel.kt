package com.example.uiassignment.viewlayer.composeui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.data_layer.Database
import com.example.uiassignment.miscellaneous.Model
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(database: Database) :ViewModel() {
    private val _listOfModels = MutableStateFlow(database.getModels())
    val listOfModels: StateFlow<List<Model>> = _listOfModels
}