package com.hardi.newsapp.ui.newslist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hardi.newsapp.R
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.databinding.ActivityNewsListBinding
import com.hardi.newsapp.ui.base.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsListActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityNewsListBinding

    @Inject
    lateinit var newsListAdapter: NewsListAdapter

    private lateinit var newsListViewModel: NewsListViewModel

    companion object {

        const val SOURCE_ITEM = "SOURCE_ITEM"
        const val COUNTRY_CODE = "COUNTRY_CODE"
        const val LANGUAGE_CODE = "LANGUAGE_CODE"

        fun getStartIntent(
            context: Context,
            sources: String,
            countryCode: String,
            languageCode: String
        ): Intent {
            return Intent(context, NewsListActivity::class.java)
                .apply {
                    putExtra(SOURCE_ITEM, sources)
                    putExtra(COUNTRY_CODE, countryCode)
                    putExtra(LANGUAGE_CODE, languageCode)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        getIntentDataAnbdFetchData()
        setUpUI()
        setupObserver()
    }

    private fun setupViewModel() {
        newsListViewModel = ViewModelProvider(this)[NewsListViewModel::class.java]
    }

    /*Getting intent data which we are pssing throught any other activity*/
    private fun getIntentDataAnbdFetchData() {
        val source = intent.getStringExtra(SOURCE_ITEM)
        val countryCode = intent.getStringExtra(COUNTRY_CODE)
        val languageCode = intent.getStringExtra(LANGUAGE_CODE)
        if (!source.isNullOrBlank()) {
            binding.toolbar.txtTitle.text = resources.getString(R.string.news_list_by_source)
            newsListViewModel.fetchSource(source)
        } else if (!countryCode.isNullOrBlank()) {
            binding.toolbar.txtTitle.text = resources.getString(R.string.news_list_by_country)
            newsListViewModel.fetchNewsByCountry(countryCode)
        } else {
            binding.toolbar.txtTitle.text = resources.getString(R.string.news_list_by_language)
            newsListViewModel.fetchNewsByLanguage(languageCode!!)
        }
    }

    /*Setting up recyclerview layout and adding adapter*/
    private fun setUpUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = newsListAdapter
    }

    /*Using coroutine execute code in synchronized way*/
    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            if (it.data.isEmpty()) {
                                binding.progressBar.visibility = View.GONE
                                binding.recyclerView.visibility = View.GONE
                                binding.rlErrorLayout.visibility = View.VISIBLE
                                binding.tvError.text = getString(R.string.no_data_found)
                            } else {
                                binding.progressBar.visibility = View.GONE
                                binding.rlErrorLayout.visibility = View.GONE
                                renderList(it.data)
                                binding.recyclerView.visibility = View.VISIBLE
                            }
                        }

                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.rlErrorLayout.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                            binding.rlErrorLayout.visibility = View.VISIBLE
                            binding.btnTryAgain.setOnClickListener(this@NewsListActivity)
                        }
                    }
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_tryAgain -> {
                setupObserver()
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        newsListAdapter.addData(articleList)
        newsListAdapter.notifyDataSetChanged()
    }

}