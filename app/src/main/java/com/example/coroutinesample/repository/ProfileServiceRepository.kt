package com.example.coroutinesample.repository

import com.example.coroutinesample.model.Profile
import kotlinx.coroutines.Deferred

interface ProfileServiceRepository {
    suspend fun asyncFetchByName(name: String) : Profile
    suspend fun asyncFetchById(id: Long) : Profile
}