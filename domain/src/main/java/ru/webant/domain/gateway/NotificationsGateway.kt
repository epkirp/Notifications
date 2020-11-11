package ru.webant.domain.gateway

import io.reactivex.Completable
import io.reactivex.Single
import ru.webant.domain.NotificationEntity
import ru.webant.domain.PaginationEntity

interface NotificationsGateway {
    fun getNotificationsList(page: Int, limit: Int): Single<PaginationEntity<NotificationEntity>?>
    fun addNotification(notification: NotificationEntity): Completable
    fun getNewId(): Single<Int>
}