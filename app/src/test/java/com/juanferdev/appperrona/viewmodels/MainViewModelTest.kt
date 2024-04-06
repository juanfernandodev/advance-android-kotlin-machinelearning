package com.juanferdev.appperrona.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juanferdev.appperrona.MainDispatcherRule
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.main.MainViewModel
import com.juanferdev.appperrona.repositories.FakeDogRepositoryError
import com.juanferdev.appperrona.repositories.FakeDogRepositorySuccess
import com.juanferdev.appperrona.repositories.FakeSuccessClassifierRepository
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun getRecognizedDogWhenRepositoriesIsSuccessThenStatusGetDog() {
        val mainViewModel = MainViewModel(
            dogRepository = FakeDogRepositorySuccess(),
            classifierRepository = FakeSuccessClassifierRepository()
        )
        mainViewModel.getRecognizedDog("1")
        val statusValue = mainViewModel.status.value as ApiResponseStatus.Success
        val dog = statusValue.data
        assertEquals(1L, dog.id)
    }

    @Test
    fun getRecognizedDogWhenDogRepositoryIsWrongThenStatusGetError() {
        val mainViewModel = MainViewModel(
            dogRepository = FakeDogRepositoryError(),
            classifierRepository = FakeSuccessClassifierRepository()
        )
        mainViewModel.getRecognizedDog("1")
        val statusValue = mainViewModel.status.value as ApiResponseStatus.Error
        assertEquals(R.string.unknown_error, statusValue.messageId)
    }


}