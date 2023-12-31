package com.example.uiassignment.viewlayer.composeui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uiassignment.miscellaneous.trimDouble
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NumberInputViewModel: ViewModel() {
    private val _money = MutableStateFlow("0")
    val money: StateFlow<String> = _money
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading
    private val _swappingMoney = MutableStateFlow("0")
    val swappingMoney: StateFlow<String> = _swappingMoney
    private val singleDigitNum = listOf("0","1","2","3","4","5","6","7","8","9")

    fun editMoney(input:String) {
        _loading.value = true
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
        viewModelScope.launch {
            delay(500)
            _loading.value = false
            _swappingMoney.value = trimDouble(_money.value.toDouble() * 3.14,"10").toString()
        }
    }
}