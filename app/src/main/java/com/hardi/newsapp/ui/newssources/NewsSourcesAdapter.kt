package com.hardi.newsapp.ui.newssources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.hardi.newsapp.data.model.NewsSource
import com.hardi.newsapp.databinding.NewsSourcesItemLayoutBinding
import com.hardi.newsapp.ui.newslist.NewsListActivity

class NewsSourcesAdapter(
    private val newsSourceList: ArrayList<NewsSource>
) : RecyclerView.Adapter<NewsSourcesAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: NewsSourcesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(newsSource : NewsSource){
            binding.tvNewsSource.text = newsSource.name
            itemView.setOnClickListener{
                it.context.startActivity(NewsListActivity.getStartIntent(it.context, newsSource.id!!, "" , ""))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            NewsSourcesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(newsSourceList[position])
    }

    override fun getItemCount(): Int {
        return newsSourceList.size
    }

    fun addData(list: List<NewsSource>) {
        newsSourceList.addAll(list)
    }
}