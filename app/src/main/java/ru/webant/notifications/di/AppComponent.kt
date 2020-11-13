package ru.webant.notifications.di

import dagger.Component
import ru.webant.notifications.MainActivity
import ru.webant.notifications.fragments.add_notification.AddNotificationPresenter
import ru.webant.notifications.fragments.edit_notification.EditNotificationPresenter
import ru.webant.notifications.fragments.notifications_list.NotificationsPresenter
import javax.inject.Singleton

@Component(modules = [AppModule::class, GatewayModule::class])
@Singleton
interface AppComponent {
    fun provideNotificationsPresenter(): NotificationsPresenter
    fun provideEditNotificationPresenter(): EditNotificationPresenter
    fun provideAddNotificationPresenter(): AddNotificationPresenter

    fun inject(target: MainActivity)
}