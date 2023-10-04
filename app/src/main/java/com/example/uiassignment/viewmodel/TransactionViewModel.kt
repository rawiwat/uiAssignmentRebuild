package com.example.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import com.example.uiassignment.Database
import com.example.uiassignment.Model
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TransactionViewModel(database: Database,id: Int):ViewModel() {
    private val thisDatabase = database
    private val _model = MutableStateFlow(thisDatabase.getModelFromID(id))
    val model: StateFlow<Model> = _model

    fun changeModel(id: Int) {
        _model.value = thisDatabase.getModelFromID(id)
    }
}

class NumberInputViewModel: ViewModel() {
    private val _money = MutableStateFlow("0")
    val money: StateFlow<String> = _money
    private val singleDigitNum = listOf("0","1","2","3","4","5","6","7","8","9")

    fun editMoney(input:String) {
        if (_money.value == "0") {
            if (singleDigitNum.contains(input)) {
                _money.value = input
            } else if (input == ".") {
                _money.value += "."
            }
        } else {
            if (singleDigitNum.contains(input)) {
                _money.value += input
            } else if (input == ".") {
                if (_money.value.contains(".")) {
                    _money.value = _money.value.filterNot { it == '.' }
                    _money.value += "."
                } else {
                    _money.value += "."
                }
            } else if (input == "←") {
                if (_money.value.length >= 2) {
                    _money.value = _money.value.substring(0,_money.value.length - 1)
                } else {
                    _money.value = "0"
                }
            }
        }
    }
}