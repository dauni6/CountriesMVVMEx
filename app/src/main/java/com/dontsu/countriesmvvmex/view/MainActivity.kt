package com.dontsu.countriesmvvmex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dontsu.countriesmvvmex.R
import com.dontsu.countriesmvvmex.viewmodel.RecyclerViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: RecyclerViewModel
    private val countriesAdapter = CountryListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ManiActivity에 viewModel을 연결
        viewModel = ViewModelProvider(this).get(RecyclerViewModel::class.java)
        viewModel.refresh() //우선 초기화, 초기화 함으로써 MainActivity의 생명주기가 다 끝날때까지 데이터가 남아있게 된다

        countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh() //리프래쉬 시킬때 마다 다시 보여주기
        }

        observeViewModel() //UI 업데이트를 담당할거임
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer { countries ->
            countries?.let {
                countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it) } //adapter에 변화된 countries를 보내줌
        })

        viewModel.countryLoadError.observe(this, Observer { isError ->
            isError?.let { list_error.visibility = if (it) View.VISIBLE else View.GONE } //error를 보여줄지 말지
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE //로딩프로그래스바를 보여줄지 말지
                if (it) {
                    list_error.visibility = View.GONE
                    countriesList.visibility = View.GONE
                }
            }
        })
    }
}