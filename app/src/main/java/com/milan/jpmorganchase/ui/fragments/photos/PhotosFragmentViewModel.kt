package com.milan.jpmorganchase.ui.fragments.photos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milan.jpmorganchase.api.ApiState
import com.milan.jpmorganchase.models.PhotosResponse
import com.milan.jpmorganchase.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosFragmentViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    private val job = SupervisorJob()
    private val coroutineContext = Dispatchers.Main + job

    val photosRes = MutableLiveData<ApiState<PhotosResponse>>()

    fun getPhotosList(albumId: String) = viewModelScope.launch(coroutineContext) {
        photosRes.value = ApiState.loading()
        mainRepository.getPhotos(albumId).let {
            if (it.isSuccessful) {
                photosRes.value = ApiState.success(it.body())
            } else {
                photosRes.value = ApiState.error(it.errorBody().toString().trim())
            }
        }
    }
}