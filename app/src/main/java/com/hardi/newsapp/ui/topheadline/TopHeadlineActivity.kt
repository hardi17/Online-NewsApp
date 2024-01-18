package com.hardi.newsapp.ui.topheadline

import android.content.Context
import android.content.Intent
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
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.databinding.ActivityTopHeadlineBinding
import com.hardi.newsapp.di.component.DaggerActivityComponent
import com.hardi.newsapp.di.module.ActivityModule
import com.hardi.newsapp.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityTopHeadlineBinding

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlineAdapter

    @Inject
    lateinit var topHeadlineViewModel: TopHeadlineViewModel

    companion object{

        const val EXTRAS_COUNTRY = "EXTRAS_COUNTRY"

        fun getStartIntent(context: Context, country: String) : Intent{
            return Intent(context, TopHeadlineActivity::class.java)
                .apply {
                    putExtra(EXTRAS_COUNTRY,country)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentDataAnbdFetchData()
        setUpUI()
        setupObserver()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

    /*Getting intent data which we are pssing throught any other activity*/
    private fun getIntentDataAnbdFetchData() {
        val country = intent.getStringExtra(EXTRAS_COUNTRY)
        country?.let{
            topHeadlineViewModel.fetchNews(country)
        }
    }

    /*Setting up recyclerview layout and adding adapter*/
    private fun setUpUI() {
        binding.toolbar.txtTitle.text = getString(R.string.top_headlines)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = topHeadlineAdapter
    }

    /*Using coroutine execute code in synchronized way*/
    private fun setupObserver() {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                topHeadlineViewModel.uiState.collect{
                    when(it){
                            is UiState.Success -> {
                                binding.progressBar.visibility = View.GONE
                                binding.rlErrorLayout.visibility = View.GONE
                                renderList(it.data)
                                binding.recyclerView.visibility = View.VISIBLE
                            }is UiState.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.recyclerView.visibility = View.GONE
                                binding.rlErrorLayout.visibility = View.GONE
                            }is UiState.Error -> {
                                binding.progressBar.visibility = View.GONE
                                binding.recyclerView.visibility = View.GONE
                                binding.rlErrorLayout.visibility = View.VISIBLE
                                binding.btnTryAgain.setOnClickListener(this@TopHeadlineActivity)
                            }
                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        topHeadlineAdapter.addData(articleList)
        topHeadlineAdapter.notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_tryAgain ->{
                setupObserver()
            }
        }
    }


}