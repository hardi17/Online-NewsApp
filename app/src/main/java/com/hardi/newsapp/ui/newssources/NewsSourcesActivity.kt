package com.hardi.newsapp.ui.newssources

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
import com.hardi.newsapp.data.model.NewsSource
import com.hardi.newsapp.databinding.ActivityNewsSourcesBinding
import com.hardi.newsapp.ui.base.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsSourcesActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityNewsSourcesBinding

    @Inject
    lateinit var newsSourcesAdapter: NewsSourcesAdapter

    private lateinit var newsSourcesViewModel: NewsSourcesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsSourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        setUpUI()
        setupObserver()
    }

    private fun setUpViewModel() {
        newsSourcesViewModel = ViewModelProvider(this)[NewsSourcesViewModel::class.java]
    }


    /*Setting up recyclerview layout and adding news sources adapter*/
    private fun setUpUI() {
        binding.toolbar.txtTitle.text = applicationContext.resources.getString(R.string.new_sources)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = newsSourcesAdapter
    }

    /*Using coroutine execute code in synchronized way*/
    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsSourcesViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rlErrorLayout.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
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
                            binding.btnTryAgain.setOnClickListener(this@NewsSourcesActivity)
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

    private fun renderList(list: List<NewsSource>) {
        newsSourcesAdapter.addData(list)
        newsSourcesAdapter.notifyDataSetChanged()
    }

}