package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.drm.DrmStore
import android.provider.ContactsContract
import java.lang.NullPointerException
import java.sql.SQLException

class NotasDbHelper(context: Context?,
                    name:String?,
                    factory:SQLiteDatabase.CursorFactory?, version:Int): SQLiteOpenHelper
    (context, name, factory, version){

    val NOMBRE_TABLA = "notas"

    val _ID = "id"
    val TITLE = "titulo"
    val CONTENT = "contenido"

    var database:SQLiteDatabase?=null

    val QUERY_CREATE_TABLE = "CREATE TABLE $NOMBRE_TABLA " +
            "($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$TITLE TEXT NOT NULL," +
            "$CONTENT TEXT);"


    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(QUERY_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $NOMBRE_TABLA")
        onCreate(db)
    }

    @Throws(SQLException::class)
    fun open(){
        database = this.writableDatabase
    }

    override fun close() {
        super.close()
        database!!.close()
    }
    //Insertar datos a la tabla
    fun addNota(titulo:String, contenido:String){
        //necesitamos un objeto del tipo ContentValues para formr abjetos del tipo
        //clave-valo
        val contentValues = ContentValues()
        contentValues.put(TITLE,titulo)
        contentValues.put(CONTENT, contenido)

        database!!.insert(NOMBRE_TABLA, null, contentValues)

    }
    fun getListNotas():ArrayList<Nota>{

        val listaNotas = ArrayList<Nota>()
        val selectAll = "SELECT * FROM $NOMBRE_TABLA"
        val cursor: Cursor = database!!.rawQuery(selectAll, null)

        if (cursor.moveToFirst()){
            do{
                val nota = Nota().apply {
                    id = cursor.getInt(cursor.getColumnIndex(_ID))
                    titulo = cursor.getString(cursor.getColumnIndex(TITLE))
                    contenido = cursor.getString(cursor.getColumnIndex(CONTENT))
                }
                listaNotas.add(nota)
            }while (cursor.moveToNext())
        }
        return listaNotas
    }

}