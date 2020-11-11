package ru.webant.notifications.di

import dagger.Module
import dagger.Provides
import ru.webant.notifications.App
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideAppModule(): App {
        return app
    }
}