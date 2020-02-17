package pl.colorland.imageonwall.di

import android.content.Context
import com.rezolve.now.di.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.colorland.imageonwall.App
import pl.colorland.imageonwall.ui.main.MainModule
import pl.colorland.imageonwall.ui.splash.SplashModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        SplashModule::class,
        MainModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}
