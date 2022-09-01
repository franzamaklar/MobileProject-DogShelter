package com.example.adopetme.di

import com.example.adopetme.repository.DogPhotoRepositoryImpl
import com.example.adopetme.repository.DogRepositoryImpl
import com.example.adopetme.repository.UserRepositoryImpl
import com.example.adopetme.viewmodel.DogViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single {
        DogRepositoryImpl()
    }
    single {
        DogPhotoRepositoryImpl()
    }
    single {
        UserRepositoryImpl()
    }
}

val  viewModelModule = module {
    viewModel { DogViewModel(get(), get(), get()) }
}