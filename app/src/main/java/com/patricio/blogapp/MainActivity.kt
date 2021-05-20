package com.patricio.blogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseFirestore.getInstance()
        //Consulta de información a Firebase
        db.collection("Pueblos").document("TN").get().addOnSuccessListener { document ->
            document?.let {
                val color = document.getString("color")
                val population = document.getLong("population")
                Log.d("Firebase", "Popolation: $population")
                Log.d("Firebase", "Color: $color")
            }
        }.addOnFailureListener { error ->
            Log.e("FirebaseError", error.toString())
        }

        // Ingresar información a Firebase
        db.collection("Pueblos").document("HGO").set(Ciudad((2000), "Yellow"))
            .addOnSuccessListener {
                Log.d("Firebase", "Se guardó correctamente el pueblo")
            }.addOnFailureListener { error ->
            Log.e("FirebaseError", error.toString())
        }
    }

    data class Ciudad(val population: Int = 0, val color: String = "")
}