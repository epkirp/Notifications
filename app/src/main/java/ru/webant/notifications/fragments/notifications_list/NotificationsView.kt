package ru.webant.notifications.fragments.notifications_list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.domain.NotificationEntity
import ru.webant.notifications.fragments.base.pagination.BasePaginationView

interface NotificationsView : BasePaginationView<NotificationEntity> {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openEditNotificationFragment(notification: NotificationEntity)
}