package ru.webant.domain

import java.io.Serializable
import java.sql.Time

data class NotificationEntity(
    val id: Int,
    val name: String,
    val startTime: Long,
    val endTime: Long,
    val interval: Long,
    val isActive: Boolean
) : Serializable