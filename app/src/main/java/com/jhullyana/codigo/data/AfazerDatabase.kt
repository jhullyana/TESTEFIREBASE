package com.jhullyana.codigo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Afazer::class], version = 1)
abstract class AfazerDatabase: RoomDatabase() {

    abstract fun afazerDao(): AfazerDao

    companion object {
        fun abrirBancoDeDados(context: Context): AfazerDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AfazerDatabase::class.java, "afazer.db"
            ).build()
        }
    }
}