package com.jhullyana.codigo.data

import kotlinx.coroutines.flow.Flow


interface IRepository {

    fun listarAfazeres(): Flow<List<Afazer>>
    suspend fun buscarAfazerPorId(idx: Int): Afazer?
    suspend fun gravarAfazer(afazer: Afazer)
    suspend fun excluirAfazer(afazer: Afazer)
}