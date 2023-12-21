package com.hardi.newsapp.ui.countryorlanguage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hardi.newsapp.R
import com.hardi.newsapp.data.model.CountryOrLanguage
import com.hardi.newsapp.databinding.ActivityCountryOrLanguageBinding

class CountryOrLanguageActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_FLAG = "EXTRA_FLAG"

        fun getStartIntent(context: Context, isCountry: Boolean): Intent {
            return Intent(context, CountryOrLanguageActivity::class.java)
                .apply {
                    putExtra(EXTRA_FLAG, isCountry)
                }
        }
    }

    private lateinit var binding: ActivityCountryOrLanguageBinding

    private lateinit var countryAdapter: CountryOrLanguageAdapter

    private lateinit var resultList: List<CountryOrLanguage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryOrLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()
        getIntentDataAndFetch()
    }

    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
    }

    private fun getIntentDataAndFetch() {
        val isCountry = intent.getBooleanExtra(EXTRA_FLAG, true)
        if (isCountry) {
            binding.toolbar.txtTitle.text = resources.getString(R.string.country_selection)
            createListAndSet(isCountry)
        } else {
            binding.toolbar.txtTitle.text = resources.getString(R.string.language_Selection)
            createListAndSet(isCountry)
        }
    }

    private fun createListAndSet(flag: Boolean) {
        if (flag) {
            val names = resources.getStringArray(R.array.country_name)
            val codes = resources.getStringArray(R.array.country_code)

            resultList = names.mapIndexed { index, names ->
                CountryOrLanguage(names, codes[index])
            }
        } else {
            val names = resources.getStringArray(R.array.language_names)
            val codes = resources.getStringArray(R.array.language_codes)

            resultList = names.mapIndexed { index, names ->
                CountryOrLanguage(names, codes[index])
            }
        }

        countryAdapter = CountryOrLanguageAdapter(resultList,flag)
        binding.recyclerView.adapter = countryAdapter
    }
}