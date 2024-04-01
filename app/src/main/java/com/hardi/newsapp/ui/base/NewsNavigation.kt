package com.hardi.newsapp.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hardi.newsapp.ui.countryactivity.CountryListRoute
import com.hardi.newsapp.ui.languageactivity.LanguageListRoute
import com.hardi.newsapp.ui.newslist.NewsListRoute
import com.hardi.newsapp.ui.newssources.NewsSourceRoute
import com.hardi.newsapp.ui.searchactivity.SearchNewsRoute
import com.hardi.newsapp.ui.topheadline.TopHeadlineroute

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigationBar() {

    val navHostController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navHostController, routeList)
        }
    ) { paddingValue: PaddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = Route.TopHeadline.name,
            modifier = Modifier.padding(paddingValue)
        ) {
            composable(route = Route.TopHeadline.name) {
                TopHeadlineroute(
                    onNewsClick = {
                        openCustomChromeTab(context, it)
                    }
                )
            }
            composable(route = Route.NewsSource.name) {
                NewsSourceRoute(
                    onSourceClick = {
                        navHostController.navigate(
                            Route.NewsList.passArgs(
                                it,
                                "{countryId}",
                                "{langId}"
                            )
                        )
                    }
                )
            }
            composable(route = Route.LanguageList.name) {
                LanguageListRoute(
                    onLanguageClick = {
                        navHostController.navigate(
                            Route.NewsList.passArgs(
                                "{sourceId}",
                                "{countryId}",
                                it
                            )
                        )

                    }
                )
            }
            composable(route = Route.CountryList.name) {
                CountryListRoute(
                    onCountryClick = {
                        navHostController.navigate(
                            Route.NewsList.passArgs(
                                "{sourceId}",
                                it,
                                "{langId}"
                            )
                        )
                    }
                )
            }
            composable(route = Route.SearchNews.name) {
                SearchNewsRoute(
                    onBakPress = {
                        navHostController.popBackStack()
                    },
                    onSearchNewsClick = {
                        openCustomChromeTab(context, it)
                    }
                )
            }
            composable(
                route = Route.NewsList.name,
                arguments = listOf(
                    navArgument("sourceId") { type = NavType.StringType },
                    navArgument("countryId") { type = NavType.StringType },
                    navArgument("langId") { type = NavType.StringType }
                )) {
                NewsListRoute(
                    onBakPress = {
                        navHostController.popBackStack()
                    },
                    onNewsClick = {
                        openCustomChromeTab(context, it)
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, routeList: List<Route>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = routeList.any { it.name == currentDestination?.route }

    if (bottomBarDestination) {
        NavigationBar {
            routeList.forEach { item ->
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White
                    ),
                    selected = currentDestination?.hierarchy?.any { it.route == item.name } == true,
                    onClick = {
                        navController.navigate(item.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            this@navigate.launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = null)
                    },
                    label = {
                        Text(text = stringResource(id = item.rsId))
                    }
                )
            }
        }
    }
}

fun openCustomChromeTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}