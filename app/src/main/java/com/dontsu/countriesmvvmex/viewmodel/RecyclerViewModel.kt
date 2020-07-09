package com.dontsu.countriesmvvmex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dontsu.countriesmvvmex.di.DaggerApiComponent
import com.dontsu.countriesmvvmex.model.CountriesService
import com.dontsu.countriesmvvmex.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecyclerViewModel: ViewModel() {

    @Inject
    lateinit var countriesService: CountriesService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

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
        loading.value = true //로딩을 먼저 보여줌
        disposable.add(
            countriesService.getCountries().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(value: List<Country>?) {
                        countries.value = value
                        countryLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        countryLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    override fun onCleared() { //viewModel이 연결된 Activity의 생명주기가 끝날때 얘도 clear됨. 여기서 disposable을 해제해주면 된다.
        super.onCleared()
        disposable.clear()
    }

}