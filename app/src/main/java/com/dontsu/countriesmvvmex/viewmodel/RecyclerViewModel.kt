package com.dontsu.countriesmvvmex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dontsu.countriesmvvmex.model.Country

class RecyclerViewModel: ViewModel() {

    //아래 3개의 필드가 Data class와 상호작용하여, 변경이 있을시에 UI에 교체되는 녀석들이다.
    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    //외부에서 호출은 이 녀석으로!
    fun refresh() {
        fetchCountries()
    }

    //refresh()에서 불려진다. refresh에서 바로 호출하면, 캡슐화가 안되어 보안의 문제가 생기기 때문!
    private fun fetchCountries() {
        val mockData = listOf(
            Country("CountryA"),
            Country("CountryB"),
            Country("CountryC"),
            Country("CountryD"),
            Country("CountryE"),
            Country("CountryF"),
            Country("CountryG"),
            Country("CountryH"),
            Country("CountryI"),
            Country("CountryJ")
        )
        countryLoadError.value = false
        loading.value = false
        countries.value = mockData
    }

}