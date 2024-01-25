package com.hardi.newsapp.ui.countryactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hardi.newsapp.data.model.LocaleInfo
import com.hardi.newsapp.databinding.CountryOrLanguageLayoutBinding
import com.hardi.newsapp.ui.newslist.NewsListActivity

class CountriesAdapter(
    private val countryOrLangList: List<LocaleInfo>
) : RecyclerView.Adapter<CountriesAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: CountryOrLanguageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(countryOrLangList: LocaleInfo) {
            binding.tvCountryOrLanguageName.text = countryOrLangList.name
            itemView.setOnClickListener {
                    it.context.startActivity(
                        NewsListActivity.getStartIntent(
                            it.context,
                            "",
                            countryOrLangList.code,
                            ""
                        )
                    )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            CountryOrLanguageLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(countryOrLangList[position])
    }

    override fun getItemCount(): Int {
        return countryOrLangList.size
    }


}