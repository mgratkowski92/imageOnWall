package pl.colorland.imageonwall

import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import pl.colorland.imageonwall.di.DaggerApplicationComponent

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }
}

