package com.example.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.Database
import com.example.uiassignment.Model
import com.example.uiassignment.TokenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SwapViewModel(database: Database, id: Int): ViewModel() {
    private val thisDatabase = database
    private val _listOfModels = MutableStateFlow(thisDatabase.getModels())
    val listOfModels: StateFlow<List<Model>> = _listOfModels
    private val _model = MutableStateFlow(thisDatabase.getModelFromID(id))
    val model: StateFlow<Model> = _model
    private val _tokenChosen = MutableStateFlow(false)
    val tokenChosen:StateFlow<Boolean> = _tokenChosen
    private val _token = MutableStateFlow(thisDatabase.getTokenFromID(1))
    val token: StateFlow<TokenModel> = _token
    private val _tokens = MutableStateFlow(thisDatabase.getTokens())
    val tokens: StateFlow<List<TokenModel>> = _tokens

    fun changeModel(id: Int) {
        _model.value = thisDatabase.getModelFromID(id)
    }

    fun changeToken(id: Int) {
        _token.value = thisDatabase.getTokenFromID(id)
        _tokenChosen.value = true
    }

}