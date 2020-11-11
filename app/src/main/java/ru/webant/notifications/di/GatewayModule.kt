package ru.webant.notifications.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.realm.Realm
import ru.webant.domain.gateway.NotificationsGateway
import ru.webant.gateway.RealmNotificationsGateway
import javax.inject.Singleton

@Module(includes = [RealmModule::class])
class GatewayModule {

    @Provides
    @Singleton
    fun provideNotificationsGateway(realm: Realm, scheduler: Scheduler): NotificationsGateway {
        return RealmNotificationsGateway(realm, scheduler)
    }
}