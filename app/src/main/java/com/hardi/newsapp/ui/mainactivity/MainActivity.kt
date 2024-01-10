package com.hardi.newsapp.ui.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hardi.newsapp.R
import com.hardi.newsapp.databinding.ActivityMainBinding
import com.hardi.newsapp.ui.countryactivity.CountriesActivity
import com.hardi.newsapp.ui.languageactivity.LanguagesActivity
import com.hardi.newsapp.ui.newssources.NewsSourcesActivity
import com.hardi.newsapp.ui.searchactivity.SearchActivity
import com.hardi.newsapp.ui.topheadline.TopHeadlineActivity
import com.hardi.newsapp.utils.AppConstant.COUNTRY

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.txtTitle.text = applicationContext.resources.getString(R.string.home)
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding.btnTopHeadlines.setOnClickListener(this)
        binding.btnNewSources.setOnClickListener(this)
        binding.btnCountry.setOnClickListener(this)
        binding.btnLanguages.setOnClickListener(this)
        binding.btnSearch.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_topHeadlines -> {
                startActivity(TopHeadlineActivity.getStartIntent(this@MainActivity, COUNTRY))
            }
            R.id.btn_newSources -> {
                startActivity(Intent(this@MainActivity,NewsSourcesActivity::class.java))
            }
            R.id.btn_country -> {
                startActivity(Intent(this@MainActivity,CountriesActivity::class.java))
            }
            R.id.btn_languages -> {
                startActivity(Intent(this@MainActivity,LanguagesActivity::class.java))
            }
            R.id.btn_search -> {
                startActivity(Intent(this@MainActivity,SearchActivity::class.java))
            }
        }
    }

}