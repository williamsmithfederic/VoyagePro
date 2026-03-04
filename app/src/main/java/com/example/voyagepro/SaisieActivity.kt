package com.example.voyagepro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SaisieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saisie)

        // Menu navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_saisie
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_historique -> {
                    startActivity(Intent(this, HistoriqueActivity::class.java))
                    true
                }
                R.id.nav_resume -> {
                    Toast.makeText(this, "Remplissez d'abord le formulaire", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }

        val etNom         = findViewById<EditText>(R.id.etNom)
        val etVol         = findViewById<EditText>(R.id.etVol)
        val etHebergement = findViewById<EditText>(R.id.etHebergement)
        val etRepas       = findViewById<EditText>(R.id.etRepas)
        val btnCalculer   = findViewById<Button>(R.id.btnCalculer)

        btnCalculer.setOnClickListener {
            if (etNom.text.isEmpty() || etVol.text.isEmpty() ||
                etHebergement.text.isEmpty() || etRepas.text.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nom         = etNom.text.toString()
            val vol         = etVol.text.toString().toDouble()
            val hebergement = etHebergement.text.toString().toDouble()
            val repas       = etRepas.text.toString().toDouble()
            val total       = vol + hebergement + repas

            val contribution = when {
                total > 2000  -> total * 0.10
                total >= 1000 -> total * 0.05
                else          -> total * 0.015
            }

            val intent = Intent(this, ResumeActivity::class.java)
            intent.putExtra("nom",          nom)
            intent.putExtra("vol",          vol)
            intent.putExtra("hebergement",  hebergement)
            intent.putExtra("repas",        repas)
            intent.putExtra("total",        total)
            intent.putExtra("contribution", contribution)
            startActivity(intent)
        }
    }
}