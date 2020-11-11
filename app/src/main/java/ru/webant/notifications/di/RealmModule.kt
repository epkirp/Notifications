package ru.webant.notifications.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class RealmModule {

    @Provides
    @Singleton
    fun provideRealm(scheduler: Scheduler, context: Context): Realm {
        Realm.init(context)
        RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
            .let(Realm::setDefaultConfiguration)

        var realm: Realm? = null
        val lock = CountDownLatch(1)

        scheduler.scheduleDirect {
            realm = Realm.getDefaultInstance()
            lock.countDown()
        }

        lock.await()

        return realm!!
    }

    @Provides
    @Singleton
    fun provideRealmScheduler(): Scheduler {
        return Schedulers.from(Executors.newSingleThreadExecutor())
    }
}