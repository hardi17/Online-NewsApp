package com.hardi.newsapp.data.repository

import android.provider.ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hardi.newsapp.data.api.NetworkService
import com.hardi.newsapp.data.model.Article
import com.hardi.newsapp.utils.AppConstant.DEFAULT_COUNTRY
import com.hardi.newsapp.utils.AppConstant.INITIAL_PAGE
import com.hardi.newsapp.utils.AppConstant.PAGE_SIZE

class PaginationTopheadline(
    private val networkService: NetworkService
) :
    PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: INITIAL_PAGE

            val response = networkService.getTopHeadlinesPagination(
                country = DEFAULT_COUNTRY,
                page = page,
                pageSize = PAGE_SIZE
            )

            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == INITIAL_PAGE) null else page.minus(1),
                nextKey = if (response.articles.isEmpty()) null else page.plus(1),
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}