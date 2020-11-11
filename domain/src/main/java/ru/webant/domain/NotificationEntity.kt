package ru.webant.domain

import java.io.Serializable
import java.sql.Time

data class NotificationEntity(
    val id: Int,
    val name: String,
    val startTime: Time,
    val endTime: Time,
    val interval: Time,
    val isActive: Boolean
) : Serializable