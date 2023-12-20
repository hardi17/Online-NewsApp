package com.hardi.newsapp.ui.countryorlanguage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hardi.newsapp.data.model.Country
import com.hardi.newsapp.databinding.ActivityNewsListBinding
import com.hardi.newsapp.databinding.CountryOrLanguageLayoutBinding
import com.hardi.newsapp.databinding.TopHeadlineItemLayoutBinding
import com.hardi.newsapp.ui.newslist.NewsListActivity
import com.hardi.newsapp.ui.newslist.NewsListAdapter

class CountryOrLanguageAdapter (
   private val countries: List<Country>
) : RecyclerView.Adapter<CountryOrLanguageAdapter.DataViewHolder>(){

    class DataViewHolder(private val binding: CountryOrLanguageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(countries: Country) {
            binding.tvCountryOrLanguageName.text = countries.countryName
            itemView.setOnClickListener{
                it.context.startActivity(NewsListActivity.getStartIntent(it.context, "", countries.countrCode))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            CountryOrLanguageLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int {
        return countries.size
    }

}