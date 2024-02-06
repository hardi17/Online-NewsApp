package com.hardi.newsapp.ui.newslist

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hardi.newsapp.R
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.databinding.TopHeadlineItemLayoutBinding
import com.hardi.newsapp.ui.topheadline.TopHeadlineAdapter

class NewsListAdapter(
    private val articleList: ArrayList<Article>
) : RecyclerView.Adapter<NewsListAdapter.DataViewHolder>(){

    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.textViewTitle.text = article.title
            binding.textViewDescription.visibility = View.VISIBLE
            binding.textViewDescription.text = article.description
            binding.textViewSource.text = article.source.name
            Glide.with(binding.imageViewBanner.context)
                .load(article.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imageViewBanner)

            itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, Uri.parse(article.url))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
         TopHeadlineItemLayoutBinding.inflate( LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    fun addData(newsItem: List<Article>) {
        articleList.addAll(newsItem)
    }
}