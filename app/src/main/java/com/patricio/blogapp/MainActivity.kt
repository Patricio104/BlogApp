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
        //Consulta de información a Firebase "Tiempo real"
        db.collection("Pueblos").document("TN").addSnapshotListener{ value, error ->
            value?.let {document ->
                val ciudad = document.toObject(Ciudad::class.java)
                Log.d("Firebase", "Popolation: ${ciudad?.color}")
                Log.d("Firebase", "Color: ${ciudad?.population}")
                Log.d("Firebase", "PC: ${ciudad?.pc}")
            }
        }

        // Ingresar información a Firebase
        db.collection("Pueblos").document("HGO").set(Ciudad((2000), "Yellow"))
            .addOnSuccessListener {
                Log.d("Firebase", "Se guardó correctamente el pueblo")
            }.addOnFailureListener { error ->
                Log.e("FirebaseError", error.toString())
            }
    }

    data class Ciudad(
        val population: Int = 0,
        val color: String = "",
        val pc: Int = 0
    )
}