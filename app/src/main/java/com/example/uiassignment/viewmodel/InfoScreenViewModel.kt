package com.example.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.FakeData
import com.example.uiassignment.Model
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InfoScreenViewModel:ViewModel() {
    private val _listOfModels = MutableStateFlow(FakeData().getModels())
    val listOfModels: StateFlow<List<Model>> = _listOfModels

}