package com.patricio.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Timestamp
import com.patricio.blogapp.R
import com.patricio.blogapp.data.model.Post
import com.patricio.blogapp.databinding.FragmentHomeScreenBinding
import com.patricio.blogapp.ui.home.adapter.HomeScreenAdapter


class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {
    private lateinit var binding: FragmentHomeScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        val postList = listOf(Post("https://www.latercera.com/resizer/2RS-uIf1qFGYeuURsZvOlObPlnc=/900x600/smart/arc-anglerfish-arc2-prod-copesa.s3.amazonaws.com/public/ETXWRW25XBFVJG3I36SU4Q2LDY.jpg",
                "Patrick", Timestamp.now(),
                "https://dragoz.webnode.cl/_files/system_preview_detail_200000001-329d533971-public/dragon-ball-z-640.jpg"),
                Post("https://www.latercera.com/resizer/2RS-uIf1qFGYeuURsZvOlObPlnc=/900x600/smart/arc-anglerfish-arc2-prod-copesa.s3.amazonaws.com/public/ETXWRW25XBFVJG3I36SU4Q2LDY.jpg",
                        "Patrick", Timestamp.now(),
                        "https://www.facebook.com/photo?fbid=2891006231126309&set=pcb.2891006267792972"),
                Post("https://www.latercera.com/resizer/2RS-uIf1qFGYeuURsZvOlObPlnc=/900x600/smart/arc-anglerfish-arc2-prod-copesa.s3.amazonaws.com/public/ETXWRW25XBFVJG3I36SU4Q2LDY.jpg",
                        "Patrick", Timestamp.now(),
                        "https://dragoz.webnode.cl/_files/system_preview_detail_200000001-329d533971-public/dragon-ball-z-640.jpg")
        )

        binding.rvHome.adapter = HomeScreenAdapter(postList)
    }

}