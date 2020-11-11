package ru.webant.notifications.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.webant.notifications.App
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class ContextModule {

    @Provides
    @Singleton
    fun provideContext(app: App): Context {
        return app.applicationContext
    }
}