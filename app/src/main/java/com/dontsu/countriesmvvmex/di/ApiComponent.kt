package com.dontsu.countriesmvvmex.di

import com.dontsu.countriesmvvmex.model.CountriesService
import com.dontsu.countriesmvvmex.viewmodel.RecyclerViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: CountriesService)

    fun inject(viewModel: RecyclerViewModel)
}