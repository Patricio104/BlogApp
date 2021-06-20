package com.patricio.blogapp.ui.camera

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.patricio.blogapp.R
import com.patricio.blogapp.core.Result
import com.patricio.blogapp.data.remote.camera.CameraDataSource
import com.patricio.blogapp.databinding.FragmentCameraBinding
import com.patricio.blogapp.domain.camera.CameraRepoImpl
import com.patricio.blogapp.presentation.camera.CameraViewModel
import com.patricio.blogapp.presentation.camera.CameraViewModelFactory

class CameraFragment : Fragment(R.layout.fragment_camera) {
    private val REQUEST_IMAGE_CAPTURE = 2
    private lateinit var binding: FragmentCameraBinding
    private var bitmap: Bitmap? = null
    private val viewModel by viewModels<CameraViewModel> {
        CameraViewModelFactory(
            CameraRepoImpl(CameraDataSource())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view)
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No se encontró ninguna app para abrir la camara", Toast.LENGTH_SHORT).show()
        }

        binding.btnUploadPhoto.setOnClickListener {
            bitmap?.let {
                viewModel.uploadPhoto(it, binding.etxDescription.text.toString().trim()).observe(viewLifecycleOwner, { result ->
                    when (result) {
                        is Result.Loading -> {
                            Toast.makeText(requireContext(), "Uploading photo", Toast.LENGTH_SHORT).show()
                        }
                        is Result.Success -> {
                            findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                        }
                        is Result.Failure -> {
                            Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.postImage.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }
}