package com.hardi.newsapp.ui.languageactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hardi.newsapp.data.model.CountryOrLanguage
import com.hardi.newsapp.databinding.CountryOrLanguageLayoutBinding
import com.hardi.newsapp.ui.newslist.NewsListActivity

class LanguagesAdapter(
    private val countryOrLangList: List<CountryOrLanguage>
) : RecyclerView.Adapter<LanguagesAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: CountryOrLanguageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(countryOrLangList: CountryOrLanguage) {
            binding.tvCountryOrLanguageName.text = countryOrLangList.name
            itemView.setOnClickListener {
                it.context.startActivity(
                    NewsListActivity.getStartIntent(
                        it.context,
                        "",
                        "",
                        countryOrLangList.code
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