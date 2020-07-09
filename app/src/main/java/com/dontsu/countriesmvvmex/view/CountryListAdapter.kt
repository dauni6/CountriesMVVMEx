package com.dontsu.countriesmvvmex.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dontsu.countriesmvvmex.R
import com.dontsu.countriesmvvmex.model.Country
import com.dontsu.countriesmvvmex.util.getProgressDrawable
import com.dontsu.countriesmvvmex.util.loadImage
import kotlinx.android.synthetic.main.item_countries.view.*
import java.util.ArrayList

class CountryListAdapter(var countries: ArrayList<Country>): RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_countries, parent, false)
        return CountryViewHolder(layout)
    }

    override fun getItemCount(): Int = countries.size //나라 아이템의 갯수

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position]) //각각의 나라
    }

    inner class CountryViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val countryName = view.name
        private val countryImageView = view.imageView
        private val countryCapital = view.capital
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(country: Country) {
            countryName.text = country.countryName
            countryCapital.text = country.countryCapital
            countryImageView.loadImage(country.countryFlag, progressDrawable)
        }
    }

}