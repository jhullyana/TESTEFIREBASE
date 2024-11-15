package com.jhullyana.codigo.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

// INFRAESTRUTURA DE UI
@Composable
fun ListarAfazeresScreen(
    //db: AfazerDatabase,
    viewModel: AfazerViewModel,
    navController: NavController
){

    var coroutineScope = rememberCoroutineScope()

    // Popular e carregar dados do banco
    //var afazeres by remember { mutableStateOf(listOf<Afazer>()) }
    val afazeres by viewModel.afazeres.collectAsState()

//    LaunchedEffect(Unit) {
//        coroutineScope.launch {
//            if(db.afazerDao().listarAfazeres().isEmpty()){
//                db.afazerDao().gravarAfazer(
//                    Afazer(titulo="A", descricao = "A")
//                )
//                db.afazerDao().gravarAfazer(
//                    Afazer(titulo="B", descricao = "B")
//                )
//                db.afazerDao().gravarAfazer(
//                    Afazer(titulo="C", descricao = "C")
//                )
//            }
//            afazeres = db.afazerDao().listarAfazeres()
//        }
//    }


    Column(
        modifier = Modifier.padding(
            top =  90.dp,
            start = 30.dp,
            end = 30.dp,
            bottom = 30.dp
        )
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Lista de afazeres",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(10.dp))

        for(afazer in afazeres){
            Row {
                Text(text = afazer.titulo,
                    fontSize = 30.sp)

                Button(onClick = {
                    //Navegação editar
                    navController.navigate("editarAfazer/${afazer.id}")
                }) {
                    Text(text = "Editar", fontSize = 25.sp)
                }


                Button(onClick = {
                    coroutineScope.launch {
//                        db.afazerDao().excluirAfazer(afazer)
//                        afazeres = db.afazerDao().listarAfazeres()
                        viewModel.excluir(afazer)
                    }
                }) {
                    Text(text = "Excluir", fontSize = 25.sp)
                }
            }

        }

        Button(onClick = {
            navController.navigate("incluirAfazeres")
        }) {
            Text(text = "Novo Afazer")
        }
    }
}