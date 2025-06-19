package com.alfacomics.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfacomics.data.repository.ComicRepository
import com.alfacomics.pratilipitv.data.repository.Comic
import com.alfacomics.pratilipitv.data.repository.MotionComic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ComicRepository
) : ViewModel() {
    private val _comics = MutableStateFlow<List<Comic>>(emptyList())
    val comics: StateFlow<List<Comic>> = _comics

    private val _motionComics = MutableStateFlow<List<MotionComic>>(emptyList())
    val motionComics: StateFlow<List<MotionComic>> = _motionComics

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchComics()
        fetchMotionComics()
    }

    fun fetchComics() {
        viewModelScope.launch {
            val result = repository.getComics()
            result.onSuccess { _comics.value = it }
            result.onFailure { _error.value = it.message }
        }
    }

    fun fetchMotionComics() {
        viewModelScope.launch {
            val result = repository.getMotionComics()
            result.onSuccess { _motionComics.value = it }
            result.onFailure { _error.value = it.message }
        }
    }
}