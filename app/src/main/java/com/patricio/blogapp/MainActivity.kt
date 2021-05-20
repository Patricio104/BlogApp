package com.patricio.blogapp

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private val REQUEST_IMAGE_CAPTURE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binTakePicture = findViewById<Button>(R.id.btn_take_picture)
        imageView = findViewById(R.id.imageView)

        binTakePicture.setOnClickListener{
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }catch (e: ActivityNotFoundException){
            Toast.makeText(this, "No se encontró una app para tomar la foto", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val imageBitmat = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmat)
            uploadPicture(imageBitmat)
        }
    }

    private fun uploadPicture(bitmap: Bitmap){
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef = storageRef.child("imagenes/${UUID.randomUUID()}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imagesRef.putBytes(data)

        uploadTask.continueWithTask{task ->
            if (!task.isSuccessful){
                task.exception?.let {exception ->
                    throw exception
                }
            }
            imagesRef.downloadUrl
        }.addOnCompleteListener{task ->
            if (task.isSuccessful){
                val downloadUrl = task.result.toString()
                Log.d("Storage", "uploadPictureURL : $downloadUrl")
            }
        }
    }
}