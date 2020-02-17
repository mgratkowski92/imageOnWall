package pl.colorland.imageonwall.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> constructor(
    private val viewModelType: Class<VM>
) : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: VM
    val disposables by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelType)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}
