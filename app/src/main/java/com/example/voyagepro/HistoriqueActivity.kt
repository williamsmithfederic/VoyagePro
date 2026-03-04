package com.example.voyagepro

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.Toast

class HistoriqueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historique)

        chargerHistorique()

        // Menu navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_historique
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_saisie -> {
                    startActivity(Intent(this, SaisieActivity::class.java))
                    true
                }
                R.id.nav_resume -> {
                    Toast.makeText(this, "Remplissez d'abord le formulaire", android.widget.Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        chargerHistorique()
    }

    private fun chargerHistorique() {
        val db       = DatabaseHelper(this)
        val depenses = db.getDepenses()
        val listView = findViewById<ListView>(R.id.listView)

        val liste = depenses.map { depense ->
            hashMapOf(
                "nom"    to depense["nom"]!!,
                "total"  to "Total : $ %.2f".format(depense["total"]!!.toDouble()),
                "contrib" to "+ $ %.2f".format(depense["contribution"]!!.toDouble())
            )
        }

        val adapter = SimpleAdapter(
            this, liste,
            R.layout.item_depense,
            arrayOf("nom", "total", "contrib"),
            intArrayOf(R.id.tvItemNom, R.id.tvItemTotal, R.id.tvItemContrib)
        )

        listView.adapter = adapter
    }
}