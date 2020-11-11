package ru.webant.notifications

import android.app.Application
import ru.webant.notifications.di.AppComponent
import ru.webant.notifications.di.AppModule
import ru.webant.notifications.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}