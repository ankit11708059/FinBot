package com.example.finebot.presentation.ui.Patterns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finebot.network.responses.Symbol
import com.example.finebot.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatternsViewModel @Inject constructor(private val repository: CoinRepository): ViewModel() {

    private val _users = MutableStateFlow<List<Symbol>>(listOf<Symbol>())
    val users: StateFlow<List<Symbol>> = _users

    private val _latestPrice = MutableStateFlow<HashMap<String, Int>>(HashMap())
    val latestPrice: StateFlow<HashMap<String, Int>> = _latestPrice

    fun getCoins() {
        viewModelScope.launch {
            val result = repository.getCoins().symbols
            _users.value = result
        }
    }
}