package com.jhullyana.codigo.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AfazeresNavHost(
    viewModel: AfazerViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "listarAfazeres"
    )
    {
        composable("listarAfazeres") {
            ListarAfazeresScreen(viewModel, navController)
        }
        composable("incluirAfazeres") {
            IncluirEditarAfazerScreen(viewModel = viewModel, navController = navController)
        }
        composable("editarAfazer/{afazerId}") { navRequest ->
            val afazerId = navRequest.arguments?.getString("afazerId")
            IncluirEditarAfazerScreen(afazerId?.toInt(), viewModel, navController)
        }
    }
}
