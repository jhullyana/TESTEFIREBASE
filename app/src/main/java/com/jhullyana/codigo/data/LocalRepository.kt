package com.jhullyana.codigo.data

import kotlinx.coroutines.flow.Flow

class LocalRepository(
    private val dao : AfazerDao
) : IRepository {

    override fun listarAfazeres(): Flow<List<Afazer>> {
        return dao.listarAfazeres()
    }

    override suspend fun buscarAfazerPorId(idx: Int): Afazer {
        return dao.buscarAfazerPorId(idx)
    }

    override suspend fun gravarAfazer(afazer: Afazer) {
        dao.gravarAfazer(afazer)
    }

    override suspend fun excluirAfazer(afazer: Afazer) {
        dao.excluirAfazer(afazer)
    }

}