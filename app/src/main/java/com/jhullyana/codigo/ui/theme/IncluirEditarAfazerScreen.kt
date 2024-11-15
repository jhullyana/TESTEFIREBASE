package com.jhullyana.codigo.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jhullyana.codigo.data.Afazer
import kotlinx.coroutines.launch

@Composable
fun IncluirEditarAfazerScreen(
    afazerId: Int? = null,
    //db: AfazerDatabase,
    viewModel: AfazerViewModel,
    navController: NavController
){

    var coroutineScope = rememberCoroutineScope()

    // Dados do novo afazer
    var titulo by remember {  mutableStateOf( "") }
    var descricao by remember { mutableStateOf( "") }

    var afazer: Afazer? by remember { mutableStateOf(null) }

    var label = if (afazerId == null) "Novo Afazer" else "Editar Afazer"

    LaunchedEffect(afazerId) {
        coroutineScope.launch {
            if(afazerId != null){
                afazer = viewModel.buscarPorId(afazerId)
                afazer?.let {
                    titulo = it.titulo
                    descricao = it.descricao
                }
            }
        }
    }

    Column(
        modifier = Modifier.padding(
            top =  90.dp,
            start = 30.dp,
            end = 30.dp,
            bottom = 30.dp
        )
    ) {
        Text(
            text = label,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            //Serve para disparar o segundo processo
            coroutineScope.launch {
                val afazerSalvar = Afazer(
                    id = afazerId,
                    titulo = titulo,
                    descricao = descricao
                )
//                db.afazerDao().gravarAfazer(afazerSalvar)
                viewModel.gravar(afazerSalvar)
                navController.popBackStack()
                //afazeres = db.afazerDao().listarAfazeres()
                //navController.navigate("listarAfazeres")
            }
        }) {
            Text(text = "Salvar", fontSize = 30.sp)
        }
    }

}