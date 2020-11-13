package ru.webant.notifications.fragments.notifications_list

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.domain.NotificationEntity
import ru.webant.notifications.fragments.base.pagination.BasePaginationView

interface NotificationsView : BasePaginationView<NotificationEntity> {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openEditNotificationFragment(notification: NotificationEntity)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openAddNotificationFragment()
}