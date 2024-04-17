package com.juanferdev.appperrona.dogdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.constants.DOG_KEY
import com.juanferdev.appperrona.constants.IS_RECOGNITION_KEY
import com.juanferdev.appperrona.constants.PROBABLES_DOG_ID_KEY
import com.juanferdev.appperrona.doglist.DogRepositoryContract
import com.juanferdev.appperrona.models.Dog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val dogRepository: DogRepositoryContract,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var dog: MutableState<Dog?> = mutableStateOf(
        savedStateHandle[DOG_KEY]
    )
        private set

    var probableDogsIds: MutableState<List<String>> =
        mutableStateOf(savedStateHandle[PROBABLES_DOG_ID_KEY] ?: emptyList())
        private set

    var isRecognition: MutableState<Boolean> =
        mutableStateOf(savedStateHandle[IS_RECOGNITION_KEY] ?: false)
        private set


    val status = mutableStateOf<ApiResponseStatus<Any>?>(null)

    fun addDogToUser(dogId: Long) {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            status.value = dogRepository.addDogToUser(dogId)
        }
    }

    fun resetApiResponseStatus() {
        status.value = null
    }
}