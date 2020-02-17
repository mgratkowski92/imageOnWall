package pl.inpost.inmobile.presentation.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import pl.colorland.imageonwall.ui.base.BaseViewModel
import pl.colorland.imageonwall.util.plusAssign
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashViewModel @Inject constructor() : BaseViewModel() {

    private val _viewState = MutableLiveData<SplashViewState>()
    val viewState: LiveData<SplashViewState> = _viewState

    fun init() {
        disposables += Observable.timer(3, TimeUnit.SECONDS).subscribe() {
            _viewState.postValue(SplashViewState.GoToMainView)
        }
    }

    sealed class SplashViewState {
        object GoToMainView : SplashViewState()
    }
}
