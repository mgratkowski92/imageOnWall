package pl.inpost.inmobile.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.colorland.imageonwall.ui.base.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _viewState = MutableLiveData<MainViewState>()
    val viewState: LiveData<MainViewState> = _viewState


    sealed class MainViewState
}
