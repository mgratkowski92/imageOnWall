package pl.colorland.imageonwall.ui.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import pl.colorland.imageonwall.di.ViewModelKey
import pl.inpost.inmobile.presentation.main.MainViewModel

@Module
abstract class MainModule {
    @ContributesAndroidInjector
    internal abstract fun activity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun viewModel(viewModel: MainViewModel): ViewModel

}
