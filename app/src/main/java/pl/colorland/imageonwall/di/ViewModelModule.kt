package pl.colorland.imageonwall.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
internal abstract class ViewModelModule {
    @Binds
    internal abstract fun factory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}
