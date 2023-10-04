package com.example.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.Database
import com.example.uiassignment.TokenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SwapViewModel(database: Database, id: Int): ViewModel() {
    private val thisDatabase = database
    val model = thisDatabase.getModelFromID(id)
    private val _tokenChosen = MutableStateFlow(false)
    val tokenChosen:StateFlow<Boolean> = _tokenChosen
    private val _token = MutableStateFlow(thisDatabase.getTokenFromID(1))
    val token: StateFlow<TokenModel> = _token
    val tokens = thisDatabase.getTokens()
    val favoritesTokens = thisDatabase.getFavoriteTokens()
    val popularTokens = thisDatabase.getPopularTokens()

    fun changeToken(id: Int) {
        _token.value = thisDatabase.getTokenFromID(id)
        _tokenChosen.value = true
    }
}