package com.jhullyana.codigo.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Afazer (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val titulo: String,
    val descricao: String,
    val concluido: Boolean = false
){
   constructor() : this(null, "", "", false)
}