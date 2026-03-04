package com.example.voyagepro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ResumeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)

        val nom          = intent.getStringExtra("nom") ?: ""
        val vol          = intent.getDoubleExtra("vol", 0.0)
        val hebergement  = intent.getDoubleExtra("hebergement", 0.0)
        val repas        = intent.getDoubleExtra("repas", 0.0)
        val total        = intent.getDoubleExtra("total", 0.0)
        val contribution = intent.getDoubleExtra("contribution", 0.0)

        findViewById<TextView>(R.id.tvNom).text          = nom
        findViewById<TextView>(R.id.tvVol).text          = "$ %.2f".format(vol)
        findViewById<TextView>(R.id.tvHebergement).text  = "$ %.2f".format(hebergement)
        findViewById<TextView>(R.id.tvRepas).text        = "$ %.2f".format(repas)
        findViewById<TextView>(R.id.tvTotal).text        = "$ %.2f".format(total)
        findViewById<TextView>(R.id.tvContribution).text = "$ %.2f".format(contribution)

        val tranche = when {
            total > 2000  -> "Plus de 2000$ → taux 10%"
            total >= 1000 -> "Tranche 1000$–2000$ → taux 5%"
            else          -> "Moins de 1000$ → taux 1.5%"
        }
        val taux = when {
            total > 2000  -> "Contribution (10%)"
            total >= 1000 -> "Contribution (5%)"
            else          -> "Contribution (1.5%)"
        }
        findViewById<TextView>(R.id.tvTranche).text      = tranche
        findViewById<TextView>(R.id.tvContribLabel).text = taux

        findViewById<Button>(R.id.btnEnregistrer).setOnClickListener {
            val db = DatabaseHelper(this)
            db.ajouterDepense(nom, vol, hebergement, repas, total, contribution)
            Toast.makeText(this, " Enregistré avec succès !", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HistoriqueActivity::class.java))
        }

        // Menu navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_resume
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_saisie -> {
                    startActivity(Intent(this, SaisieActivity::class.java))
                    true
                }
                R.id.nav_historique -> {
                    startActivity(Intent(this, HistoriqueActivity::class.java))
                    true
                }
                else -> true
            }
        }
    }
}