package com.example.sqlite

import android.content.ContentValues
import android.content.Intent
import android.drm.DrmStore.DrmObjectType.CONTENT
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Organization.TITLE
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    var myNotasDbHelper:NotasDbHelper? = null
    val DB_NAME = "notas.db"
    val DB_VERSION = 1

    var fabAdd: FloatingActionButton?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAdd = findViewById(R.id.fadAddNota)

        myNotasDbHelper = NotasDbHelper(this,DB_NAME, null, DB_VERSION)
        myNotasDbHelper!!.open()

        val notas = myNotasDbHelper!!.getListNotas()

        for (nota in notas){
            Log.d("prueba", "${nota.titulo}")
        }

        fabAdd!!.setOnClickListener {
            val i = Intent(this, AddNotaActivity::class.java)
            startActivity(i)
        }


    }
}