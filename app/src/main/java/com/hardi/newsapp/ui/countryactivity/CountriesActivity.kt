package com.hardi.newsapp.ui.countryactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hardi.NewsApplication
import com.hardi.newsapp.R
import com.hardi.newsapp.data.model.CountryOrLanguage
import com.hardi.newsapp.databinding.ActivityCountryOrLanguageBinding
import com.hardi.newsapp.di.component.DaggerActivityComponent
import com.hardi.newsapp.di.module.ActivityModule
import com.hardi.newsapp.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryOrLanguageBinding

    @Inject
    lateinit var countryAdapter: CountriesAdapter

    @Inject
    lateinit var countriesViewModel: CountriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityCountryOrLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()
        createListAndSet()
    }

    private fun setRecyclerView() {
        binding.toolbar.txtTitle.text = resources.getString(R.string.country_selection)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
    }


//    private fun createListAndSet() {
//        val names = resources.getStringArray(R.array.country_name)
//        val codes = resources.getStringArray(R.array.country_code)
//
//        resultList = names.mapIndexed { index, names ->
//            CountryOrLanguage(names, codes[index])
//        }
//
//
//        countryAdapter = CountriesAdapter(resultList)
//        binding.recyclerView.adapter = countryAdapter
//    }

    /*Using coroutine execute code in synchronized way*/
    private fun createListAndSet() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countriesViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvError.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.tvError.visibility = View.GONE
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                            binding.tvError.visibility = View.VISIBLE
                            binding.tvError.text = getString(R.string.internet_error)
                        }
                    }
                }
            }
        }
    }

    private fun renderList(resultList: List<CountryOrLanguage>) {
        countryAdapter = CountriesAdapter(resultList)
        binding.recyclerView.adapter = countryAdapter
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}