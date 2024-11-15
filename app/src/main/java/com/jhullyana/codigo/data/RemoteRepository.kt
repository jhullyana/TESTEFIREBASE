package com.jhullyana.codigo.data

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RemoteRepository() : IRepository {

    var db = FirebaseFirestore.getInstance()
    var afazerCollection = db.collection("afazeres")

    //FUNÇÃO NÃO REATIVA OU SEJA NÃO ATUALIZA
//    override fun listarAfazeres(): Flow<List<Afazer>> = flow {
//        val dados = afazerCollection.get().await()
//        val afazeres = dados.documents.mapNotNull { dado ->
//            dado.toObject(Afazer::class.java)
//        }
//        emit(afazeres)
//    }

    //FUNÇÃO REATIVA
    override fun listarAfazeres(): Flow<List<Afazer>> = callbackFlow {
        val listener = afazerCollection.addSnapshotListener {
                dados, erros ->
            if (erros != null){
                close(erros)
                return@addSnapshotListener
            }
            if (dados != null){
                val afazeres = dados.documents.mapNotNull { dado ->
                    dado.toObject(Afazer::class.java)
                }
                trySend(afazeres).isSuccess
            }
        }
        awaitClose{ listener.remove()}
    }

    suspend fun getId(): Int{
        val dados = afazerCollection.get().await()
        //Recupera o maior id do firestore no format inteiro
        val maxId = dados.documents.mapNotNull {
            it.getLong("id")?.toInt()
        }.maxOrNull() ?: 0
        return maxId + 1
    }

    override suspend fun gravarAfazer(afazer: Afazer) {
        val document: DocumentReference
        if (afazer.id == null){ // Inclusão
            afazer.id = getId()
            document = afazerCollection.document(afazer.id.toString())
        } else { // Alteração
            document = afazerCollection.document(afazer.id.toString())
        }
        document.set(afazer).await()
    }

    override suspend fun buscarAfazerPorId(idx: Int): Afazer? {
        val dados = afazerCollection.document(idx.toString()).get().await()
//        val afazer = dados.toObject(Afazer::class.java)
//        return afazer
        return dados.toObject(Afazer::class.java)
    }

    override suspend fun excluirAfazer(afazer: Afazer) {
        afazerCollection.document(afazer.id.toString()).delete().await()
    }
}