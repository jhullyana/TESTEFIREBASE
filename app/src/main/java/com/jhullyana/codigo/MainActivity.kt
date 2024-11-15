package com.jhullyana.codigo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.jhullyana.codigo.data.AfazerDatabase.Companion.abrirBancoDeDados
import com.jhullyana.codigo.data.LocalRepository
import com.jhullyana.codigo.data.RemoteRepository
import com.jhullyana.codigo.ui.theme.AfazerViewModel
import com.jhullyana.codigo.ui.theme.AfazeresNavHost
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inicialize o Firebase logo no começo do OnCreate
        FirebaseApp.initializeApp(this)

        // Inicialize o Firebase Analytics
        var mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        // Log um evento para testar a inicialização
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id_123")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "example_item")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

        // Continue com o restante do seu código

        enableEdgeToEdge()
//
//        val isLocal = true
//
//        val db = abrirBancoDeDados(this)
//        val localRepository = LocalRepository(db.afazerDao())
//        val remoteRepository = RemoteRepository()
//
//        val viewModel : AfazerViewModel
//        if(isLocal){
//            viewModel = AfazerViewModel(remoteRepository)
//        } else {
//            viewModel = AfazerViewModel(localRepository)
//        }

        val db = abrirBancoDeDados(this)
        val localRepository = LocalRepository(db.afazerDao())
        val remoteRepository = RemoteRepository()

        val viewModel: AfazerViewModel

        // Função para verificar se há internet
        fun isConnectedToInternet(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }

        if (isConnectedToInternet(this)) {
            // Se houver conexão com a internet, use o remoteRepository
            viewModel = AfazerViewModel(remoteRepository)
        } else {
            // Se não houver conexão, use o localRepository
            viewModel = AfazerViewModel(localRepository)
        }

        setContent {
                AfazeresNavHost(viewModel)
        }

    }
}