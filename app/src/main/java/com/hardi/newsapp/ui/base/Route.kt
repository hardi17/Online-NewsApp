package com.hardi.newsapp.ui.base

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Source
import androidx.compose.ui.graphics.vector.ImageVector
import com.hardi.newsapp.R
import com.hardi.newsapp.utils.AppConstant.COUNTRY_ID
import com.hardi.newsapp.utils.AppConstant.LANG_ID
import com.hardi.newsapp.utils.AppConstant.SOURCE_ID

sealed class Route(
    val name: String,
    val rsId: Int,
    val icon: ImageVector
) {
    object TopHeadline : Route("topheadline", R.string.home, Icons.Default.Home)
    object NewsSource : Route("newssource", R.string.sources, Icons.Default.Source)
    object LanguageList : Route("languagelist", R.string.languages, Icons.Default.Language)
    object CountryList : Route("countrylist", R.string.countries, Icons.Default.Place)
    object SearchNews : Route("searchnews", R.string.search, Icons.Default.Search)

    object NewsList :
        Route(
            "newslist/{$SOURCE_ID}/{$COUNTRY_ID}/{$LANG_ID}",
            R.string.new_sources,
            Icons.Default.Newspaper
        ) {
        fun passArgs(
            sourceId: String,
            countryId: String,
            langId: String,
        ): String {
            return "newslist/$sourceId/$countryId/$langId"
        }
    }
}

val routeList: List<Route> = listOf(
    Route.TopHeadline,
    Route.NewsSource,
    Route.LanguageList,
    Route.CountryList,
    Route.SearchNews
)
