package ru.webant.notifications.fragments.notifications_list

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Single
import ru.webant.domain.NotificationEntity
import ru.webant.domain.PaginationEntity
import ru.webant.domain.gateway.NotificationsGateway
import ru.webant.notifications.fragments.base.pagination.BasePaginationPresenter
import javax.inject.Inject

@InjectViewState
class NotificationsPresenter @Inject constructor(
    private val notificationsGateway: NotificationsGateway
) : BasePaginationPresenter<NotificationsView, NotificationEntity>() {

    override fun getItems(page: Int, limit: Int): Single<PaginationEntity<NotificationEntity>?> =
        notificationsGateway.getNotificationsList(page, limit)

    fun onNotificationClick(notification: NotificationEntity) {
        viewState.openEditNotificationFragment(notification)
    }

    fun onNotificationSwitched(notification: NotificationEntity, isChecked: Boolean) {
        if (isChecked) {
            //todo меняем isActive на true в бд
        } else {
            //todo меняем isActive на false в бд
        }
    }

    fun onAddNotification() {
        viewState.openAddNotificationFragment()
    }
}