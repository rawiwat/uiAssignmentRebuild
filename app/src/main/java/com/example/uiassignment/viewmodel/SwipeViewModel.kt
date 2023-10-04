package com.example.uiassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SwipeViewModel(
    private val initialOffset: Int
) : ViewModel() {
    private val _currentOffset = MutableStateFlow(initialOffset)
    val currentOffset: StateFlow<Int> = _currentOffset
    private val fullScreenHeight = initialOffset * 2

    private val _activation = MutableStateFlow(false)
    val activation:StateFlow<Boolean> = _activation

    fun changeOffset(input:Int) {
        viewModelScope.launch {
            _currentOffset.value += input
            if (currentOffset.value < 0) {
                _currentOffset.value -= currentOffset.value
            }

            if (_currentOffset.value >= (fullScreenHeight * 3 / 4)) {
                _currentOffset.value = initialOffset
                turnOff()
            }
        }
    }

    fun setOffsetToDefault() {
        _currentOffset.value = initialOffset
    }

    fun turnOff() {
        _activation.value = false
        setOffsetToDefault()
    }
}