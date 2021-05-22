package com.patricio.blogapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.patricio.blogapp.R
import com.patricio.blogapp.core.Resource
import com.patricio.blogapp.data.remote.home.HomeScreenDataSource
import com.patricio.blogapp.databinding.FragmentHomeScreenBinding
import com.patricio.blogapp.domain.home.HomeScreenRepoImpl
import com.patricio.blogapp.presentation.main.HomeScreenViewModel
import com.patricio.blogapp.presentation.main.HomeScreenViewModelFactory
import com.patricio.blogapp.ui.main.adapter.HomeScreenAdapter


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {
    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(
            HomeScreenRepoImpl(HomeScreenDataSource())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.rvHome.adapter = HomeScreenAdapter(result.data)
                    binding.progressBar.visibility = View.GONE
                }

                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Ocurrió un error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}