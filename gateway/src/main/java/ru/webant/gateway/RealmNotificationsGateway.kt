package ru.webant.gateway

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.realm.Realm
import ru.webant.FIELD_ID
import ru.webant.domain.NotificationEntity
import ru.webant.domain.PaginationEntity
import ru.webant.domain.gateway.NotificationsGateway
import ru.webant.gateway.realm_models.RealmNotificationEntity

class RealmNotificationsGateway(
    private val realm: Realm,
    private val scheduler: Scheduler
) : NotificationsGateway {
    override fun getNotificationsList(
        page: Int,
        limit: Int
    ): Single<PaginationEntity<NotificationEntity>?> = Single.defer {
        realm.beginTransaction()

        val totalItems: Int

        val notifications = realm.where(RealmNotificationEntity::class.java)
            .findAll()
            .also { totalItems = it.size }
            .drop((page - 1) * limit)
            .take(limit)
            .map(RealmNotificationEntity::toDomain)

        realm.commitTransaction()

        val paginationEntity = PaginationEntity(
            totalItems = totalItems,
            itemsPerPage = limit,
            countOfPages = totalItems / limit,
            items = notifications
        )

        Single.just(paginationEntity)
    }.subscribeOn(scheduler)


    override fun addNotification(notification: NotificationEntity): Completable =
        Completable.fromAction {
            realm.beginTransaction()
            realm.insertOrUpdate(RealmNotificationEntity.fromDomain(notification))
            realm.commitTransaction()

            Completable.complete()
        }.subscribeOn(scheduler)

    override fun getNewId(): Single<Int> = Single.defer {
        realm.beginTransaction()

        val lastId = realm.where(RealmNotificationEntity::class.java).max(FIELD_ID)
        val nextId = if (lastId == null) {
            1
        } else {
            lastId.toInt() + 1
        }
        realm.commitTransaction()
        Single.just(nextId)
    }.subscribeOn(scheduler)
}