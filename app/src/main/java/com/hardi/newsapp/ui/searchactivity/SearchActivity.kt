package com.hardi.newsapp.ui.searchactivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hardi.newsapp.R
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.databinding.ActivitySearchBinding
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.utils.KeyboardUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    @Inject
    lateinit var adapter: SearchViewAdapter

    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpUI()
        getSearchResult()
        setupObserver()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
    }

    /*Setting up recyclerview layout and adding adapter*/
    @SuppressLint("ClickableViewAccessibility")
    private fun setUpUI() {
        binding.toolbar.txtTitle.text = getString(R.string.search)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setOnTouchListener(@SuppressLint("ClickableViewAccessibility")
        object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                KeyboardUtils.closeSoftKeyboard(this@SearchActivity)
                return false
            }

        })
    }

    private fun getSearchResult() {
        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newQuery: String): Boolean {
                viewModel.searchNews(newQuery)
                return true
            }

        })
    }

    /*Using coroutine execute code in synchronized way*/
    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            if (it.data.isEmpty()) {
                                binding.progressBar.visibility = View.GONE
                                binding.recyclerView.visibility = View.GONE
                                binding.noSearchText.visibility = View.VISIBLE
                                binding.rlErrorLayout.visibility = View.GONE
                            } else {
                                binding.progressBar.visibility = View.GONE
                                binding.rlErrorLayout.visibility = View.GONE
                                binding.noSearchText.visibility = View.GONE
                                renderList(it.data)
                                binding.recyclerView.visibility = View.VISIBLE
                            }
                        }

                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.noSearchText.visibility = View.GONE
                            binding.rlErrorLayout.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                            binding.noSearchText.visibility = View.GONE
                            binding.rlErrorLayout.visibility = View.VISIBLE
                            binding.tvError.text = it.message
                        }
                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()
    }

}