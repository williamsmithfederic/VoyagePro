package com.example.voyagepro

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "voyagepro.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE depenses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom TEXT,
                vol REAL,
                hebergement REAL,
                repas REAL,
                total REAL,
                contribution REAL
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS depenses")
        onCreate(db)
    }

    // Sauvegarder une dépense
    fun ajouterDepense(nom: String, vol: Double, hebergement: Double, repas: Double, total: Double, contribution: Double) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nom", nom)
            put("vol", vol)
            put("hebergement", hebergement)
            put("repas", repas)
            put("total", total)
            put("contribution", contribution)
        }
        db.insert("depenses", null, values)
        db.close()
    }

    // Récupérer toutes les dépenses
    fun getDepenses(): List<Map<String, String>> {
        val liste = mutableListOf<Map<String, String>>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM depenses ORDER BY id DESC", null)
        while (cursor.moveToNext()) {
            liste.add(mapOf(
                "nom"          to cursor.getString(1),
                "vol"          to cursor.getDouble(2).toString(),
                "hebergement"  to cursor.getDouble(3).toString(),
                "repas"        to cursor.getDouble(4).toString(),
                "total"        to cursor.getDouble(5).toString(),
                "contribution" to cursor.getDouble(6).toString()
            ))
        }
        cursor.close()
        db.close()
        return liste
    }
}