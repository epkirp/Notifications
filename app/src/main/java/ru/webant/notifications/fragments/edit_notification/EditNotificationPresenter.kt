package ru.webant.notifications.fragments.edit_notification

import com.arellomobile.mvp.InjectViewState
import ru.webant.domain.gateway.NotificationsGateway
import ru.webant.notifications.fragments.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class EditNotificationPresenter @Inject constructor(
    private val notificationsGateway: NotificationsGateway
) : BasePresenter<EditNotificationView>() {
}