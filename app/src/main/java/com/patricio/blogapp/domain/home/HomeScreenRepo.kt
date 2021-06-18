package com.patricio.blogapp.domain.home

import com.patricio.blogapp.core.Result
import com.patricio.blogapp.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPost(): Result<List<Post>>
}