package com.example.voyagepro

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        // Afficher l'écran Saisie par défaut
        afficherActivite("saisie")

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_saisie     -> { afficherActivite("saisie");     true }
                R.id.nav_resume     -> { afficherActivite("resume");     true }
                R.id.nav_historique -> { afficherActivite("historique"); true }
                else -> false
            }
        }
    }

    private fun afficherActivite(ecran: String) {
        val intent = when (ecran) {
            "saisie"     -> android.content.Intent(this, SaisieActivity::class.java)
            "resume"     -> android.content.Intent(this, ResumeActivity::class.java)
            "historique" -> android.content.Intent(this, HistoriqueActivity::class.java)
            else         -> android.content.Intent(this, SaisieActivity::class.java)
        }
        intent.flags = android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(intent)
    }
}