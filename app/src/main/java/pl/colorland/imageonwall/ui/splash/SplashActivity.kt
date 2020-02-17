package pl.colorland.imageonwall.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.ar.core.ArCoreApk
import kotlinx.android.synthetic.main.activity_splash.*
import pl.colorland.imageonwall.R
import pl.colorland.imageonwall.ui.base.BaseActivity
import pl.colorland.imageonwall.ui.main.createMainIntent
import pl.colorland.imageonwall.util.observeNotNull
import pl.inpost.inmobile.presentation.splashscreen.SplashViewModel
import pl.inpost.inmobile.presentation.splashscreen.SplashViewModel.SplashViewState.GoToMainView

class SplashActivity : BaseActivity<SplashViewModel>(SplashViewModel::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        observeViewModel()
        //viewModel.init()
        maybeEnableArButton()
    }

    private fun observeViewModel() {
        viewModel.viewState.observeNotNull(this) {
            when (it) {
                GoToMainView -> goToMainView()
            }
        }
    }

    fun maybeEnableArButton() {
        val availability = ArCoreApk.getInstance().checkAvailability(this)
        if (availability.isTransient) {
            Handler().postDelayed({ maybeEnableArButton() }, 200)
        }
        if (availability.isSupported) {
            label.text = getString(R.string.ar_core_supported)
            arButton.visibility = View.VISIBLE
            arButton.isEnabled = true
            arButton.setOnClickListener { goToMainView() }
        } else {
            label.text = getString(R.string.ar_core_not_supported)
            arButton.visibility = View.INVISIBLE
            arButton.isEnabled = false
        }
    }

    private fun goToMainView() {
        startActivity(createMainIntent(this))
    }
}
