package com.hardi.newsapp.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {

    private val testDispatcherProvider = UnconfinedTestDispatcher()

    override val main: CoroutineDispatcher
        get() = testDispatcherProvider

    override val io: CoroutineDispatcher
        get() = testDispatcherProvider

    override val default: CoroutineDispatcher
        get() = testDispatcherProvider
}