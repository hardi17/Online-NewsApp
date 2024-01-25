package com.hardi.newsapp.ui.languageactivity

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
import com.hardi.newsapp.data.model.LocaleInfo
import com.hardi.newsapp.databinding.ActivityCountryOrLanguageBinding
import com.hardi.newsapp.di.component.DaggerActivityComponent
import com.hardi.newsapp.di.module.ActivityModule
import com.hardi.newsapp.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguagesActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCountryOrLanguageBinding

    @Inject
    lateinit var languageAdapter: LanguagesAdapter

    @Inject
    lateinit var languagesViewModel: LanguagesViewModel

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

    private fun createListAndSet() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                languagesViewModel.uiState.collect {
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
                            binding.btnTryAgain.setOnClickListener(this@LanguagesActivity)
                        }
                    }
                }
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_tryAgain ->{
                createListAndSet()
            }
        }
    }

    private fun renderList(resultList: List<LocaleInfo>) {
        languageAdapter = LanguagesAdapter(resultList)
        binding.recyclerView.adapter = languageAdapter
    }


    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}