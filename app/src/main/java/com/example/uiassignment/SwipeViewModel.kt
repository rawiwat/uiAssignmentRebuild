package com.example.uiassignment

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SwipeViewModel(
    private val initialOffset: Int,
    private val context: Context
) : ViewModel() {
    private val _currentOffset = MutableStateFlow(initialOffset)
    val currentOffset: StateFlow<Int> = _currentOffset
    private val fullScreenHeight = initialOffset * 2
    fun changeOffset(input:Int) {
        viewModelScope.launch {
            _currentOffset.value += input
            if (currentOffset.value < 0) {
                _currentOffset.value -= currentOffset.value
            }

            if (_currentOffset.value >= (fullScreenHeight * 3 / 4)) {
                _currentOffset.value = initialOffset
                val intent = Intent("Change_activate")
                intent.putExtra("change_activation_to", false)
                context.sendBroadcast(intent)
            }
        }
    }

    fun setOffsetToDefault() {
        _currentOffset.value = initialOffset
    }
}