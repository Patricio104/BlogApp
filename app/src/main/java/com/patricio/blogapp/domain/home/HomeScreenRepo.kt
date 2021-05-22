package com.patricio.blogapp.domain.home

import com.patricio.blogapp.core.Resource
import com.patricio.blogapp.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPost(): Resource<List<Post>>
}