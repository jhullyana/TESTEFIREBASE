package com.jhullyana.codigo.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhullyana.codigo.data.Afazer
import com.jhullyana.codigo.data.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AfazerViewModel(
    private val repository : IRepository
) : ViewModel(){

    private val _afazeres = MutableStateFlow<List<Afazer>>(emptyList())
    val afazeres: StateFlow<List<Afazer>> get() = _afazeres

    init {
        viewModelScope.launch {
            repository.listarAfazeres().collectLatest { listaDeAfazeres ->
                _afazeres.value = listaDeAfazeres
            } //.collectLastest
        }
    }

    fun excluir(afazer: Afazer) {
        viewModelScope.launch {
            repository.excluirAfazer(afazer)
        }
    }

    suspend fun buscarPorId(afazerId: Int): Afazer? {
        return withContext(Dispatchers.IO){
            repository.buscarAfazerPorId(afazerId)
        }
    }

    fun gravar(afazer: Afazer) {
        viewModelScope.launch {
            repository.gravarAfazer(afazer)
        }
    }

}