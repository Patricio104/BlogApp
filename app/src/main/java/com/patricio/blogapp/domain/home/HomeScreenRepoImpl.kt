package com.patricio.blogapp.domain.home

import com.patricio.blogapp.core.Result
import com.patricio.blogapp.data.model.Post
import com.patricio.blogapp.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {
    override suspend fun getLatestPost(): Result<List<Post>> = dataSource.getLatestPosts()

}