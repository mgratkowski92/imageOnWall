package pl.colorland.imageonwall.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel() : ViewModel() {

    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}