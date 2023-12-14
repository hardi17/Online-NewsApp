package com.hardi.newsapp.ui.mainactivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.hardi.newsapp.R
import com.hardi.newsapp.databinding.ActivityMainBinding
import com.hardi.newsapp.ui.topheadline.TopHeadlineActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.txtTitle.text =
            applicationContext.resources.getString(R.string.top_headlines)
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
                val intent = Intent(this@MainActivity, TopHeadlineActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_newSources -> {
                Log.d("", "New sources")
            }
            R.id.btn_country -> {
                Log.d("", "Country selection")
            }
            R.id.btn_languages -> {
                Log.d("", "Language selection")
            }
            R.id.btn_search -> {
                Log.d("", "Search more news")
            }
        }
    }

}