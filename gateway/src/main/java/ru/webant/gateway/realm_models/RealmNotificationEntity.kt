package ru.webant.gateway.realm_models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import ru.webant.domain.NotificationEntity
import java.sql.Time

@RealmClass
open class RealmNotificationEntity(
    @PrimaryKey
    open var id: Int = -1,
    open var name: String = "",
    open var startTime: Long = 0,
    open var endTime: Long = 0,
    open var interval: Long = 0,
    open var isActive: Boolean = false
) : RealmObject() {

    fun toDomain(): NotificationEntity {
        return NotificationEntity(
            id = id,
            name = name,
            startTime = startTime,
            endTime = endTime,
            interval = interval,
            isActive = isActive
        )
    }

    companion object {
        fun fromDomain(notification: NotificationEntity): RealmNotificationEntity {
            return RealmNotificationEntity(
                id = notification.id,
                name = notification.name,
                startTime = notification.startTime,
                endTime = notification.endTime,
                interval = notification.interval,
                isActive = notification.isActive
            )
        }
    }
}