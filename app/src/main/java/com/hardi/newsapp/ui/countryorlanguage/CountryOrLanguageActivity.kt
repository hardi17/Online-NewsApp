package com.hardi.newsapp.ui.countryorlanguage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hardi.newsapp.R
import com.hardi.newsapp.data.model.Country
import com.hardi.newsapp.databinding.ActivityCountryOrLanguageBinding
import com.hardi.newsapp.ui.newslist.NewsListActivity

class CountryOrLanguageActivity : AppCompatActivity() {

    companion object{

        const val EXTRA_FLAG = "EXTRA_FLAG"

        fun getStartIntent(context: Context, flag: Boolean) : Intent {
            return Intent(context, CountryOrLanguageActivity::class.java)
                .apply {
                    putExtra(EXTRA_FLAG,flag)
                }
        }
    }

    private lateinit var binding: ActivityCountryOrLanguageBinding

    private lateinit var adapter: CountryOrLanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCountryOrLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentDataAndFetch()
        createCountryListAndSet()
    }

    private fun getIntentDataAndFetch() {
        val flag = intent.getBooleanExtra(EXTRA_FLAG,true)
        if(flag){
            binding.toolbar.txtTitle.text = resources.getString(R.string.country_selection)
        }else{
            binding.toolbar.txtTitle.text = resources.getString(R.string.language_Selection)
        }
    }

    private fun createCountryListAndSet() {
        val countries = resources.getStringArray(R.array.country_name)
        val countryCodes = resources.getStringArray(R.array.country_code)

        val countryList = countries.mapIndexed { index, name ->
            Country(name, countryCodes[index])
        }

        adapter = CountryOrLanguageAdapter(countryList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )

        binding.recyclerView.adapter = adapter

    }
}