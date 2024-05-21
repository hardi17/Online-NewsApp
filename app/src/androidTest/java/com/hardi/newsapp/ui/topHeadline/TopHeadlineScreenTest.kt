package com.hardi.newsapp.ui.topHeadline

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import com.hardi.newsapp.R
import com.hardi.newsapp.ui.base.UiState
import com.hardi.newsapp.ui.topheadline.TopHeadlineScreen
import me.hardi.newsapp.data.local.entity.ArticleEntity
import me.hardi.newsapp.data.local.entity.SourceEntity
import org.junit.Rule
import org.junit.Test

class TopHeadlineScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun tophHeadlinscreen_showing_loading_whileUiStateIs_laoding() {
        composeTestRule.setContent {
            TopHeadlineScreen(
                uiState = UiState.Loading,
                onNewsClick = {})
        }

        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.resources.getString(R.string.loading))
            .assertExists()
    }

    @Test
    fun tophHeadlinscreen_showing_articleList_whileUiStateIs_sucess() {
        composeTestRule.setContent {
            TopHeadlineScreen(
                uiState = UiState.Success(testArticleList),
                onNewsClick = {})
        }

        composeTestRule
            .onNodeWithText(
                testArticleList[0].title,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()

        composeTestRule.onNode(hasScrollAction())
            .performScrollToNode(
                hasText(
                    testArticleList[2].title,
                    substring = true
                )
            )

        composeTestRule
            .onNodeWithText(
                testArticleList[2].title,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun tophHeadlinscreen_showing_error_whileUiStateIs_error() {
        val errorMsg = "Testing error"
        composeTestRule.setContent {
            TopHeadlineScreen(
                uiState = UiState.Error(errorMsg),
                onNewsClick = {})
        }

        composeTestRule
            .onNodeWithText(errorMsg)
            .assertExists()
    }
}

private val testArticleList = listOf(
    ArticleEntity(
        title = "test1",
        description = "for testing 1",
        url = "url1",
        imageUrl = "imageurl1",
        source = SourceEntity(id = "0", name = "source0")
    ),
    ArticleEntity(
        title = "test2",
        description = "for testing 2",
        url = "url2",
        imageUrl = "imageurl2",
        source = SourceEntity(id = "1", name = "source1")
    ),
    ArticleEntity(
        title = "test3",
        description = "for testing 3",
        url = "url3",
        imageUrl = "imageurl3",
        source = SourceEntity(id = "2", name = "source2")
    )
)