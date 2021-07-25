package com.milan.jpmorganchase.ui.fragments.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milan.jpmorganchase.api.ApiState
import com.milan.jpmorganchase.models.AlbumResponse
import com.milan.jpmorganchase.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumFragmentViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    private val job = SupervisorJob()
    private val coroutineContext = Dispatchers.Main + job

    private val albumRes = MutableLiveData<ApiState<AlbumResponse>>()
    val vmAlbumRes: LiveData<ApiState<AlbumResponse>>
        get() = albumRes

    init {
        getAlbumsList()
    }

    fun getAlbumsList() = viewModelScope.launch(coroutineContext) {
        albumRes.value = ApiState.loading()
        mainRepository.getAlbums().let {
            if (it.isSuccessful) {
                albumRes.value = ApiState.success(it.body())
            } else {
                albumRes.value = ApiState.error(it.errorBody().toString().trim())
            }
        }
    }
}