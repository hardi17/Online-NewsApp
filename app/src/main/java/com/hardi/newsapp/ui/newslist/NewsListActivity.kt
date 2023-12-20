package com.hardi.newsapp.ui.newslist

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
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.databinding.ActivityNewsListBinding
import com.hardi.newsapp.di.component.DaggerActivityComponent
import com.hardi.newsapp.di.module.ActivityModule
import com.hardi.newsapp.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsListBinding

    @Inject
    lateinit var newsListAdapter: NewsListAdapter

    @Inject
    lateinit var newsListViewModel: NewsListViewModel

    companion object{

        const val SOURCE_ITEM = "SOURCE_ITEM"

        fun getStartIntent(context: Context, sources: String) : Intent {
            return Intent(context, NewsListActivity::class.java)
                .apply {
                    putExtra(SOURCE_ITEM,sources)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding= ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentDataAnbdFetchData()
        setUpUI()
        setupObserver()
    }

    /*Getting intent data which we are pssing throught any other activity*/
    private fun getIntentDataAnbdFetchData() {
        val source = intent.getStringExtra(SOURCE_ITEM)
        source?.let{
            binding.toolbar.txtTitle.text = source
            newsListViewModel.fetchSource(source)
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
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                newsListViewModel.uiState.collect{
                    when(it){
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvError.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.tvError.visibility = View.GONE
                        }is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                            binding.tvError.visibility = View.VISIBLE
                            binding.tvError.text = it.message
                        }
                    }
                }
            }
        }

    }

    private fun renderList(articleList: List<Article>) {
        newsListAdapter.addData(articleList)
        newsListAdapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}