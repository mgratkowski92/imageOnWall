package pl.colorland.imageonwall.ui.splash

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.colorland.imageonwall.di.ViewModelKey
import pl.inpost.inmobile.presentation.splashscreen.SplashViewModel

@Module
abstract class SplashModule {
    @ContributesAndroidInjector
    internal abstract fun activity(): SplashActivity

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun viewModel(viewModel: SplashViewModel): ViewModel

}
