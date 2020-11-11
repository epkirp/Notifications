package ru.webant.notifications.fragments.edit_notification

import ru.webant.notifications.R
import ru.webant.notifications.fragments.base.BaseFragment

class EditNotificationFragment : BaseFragment(), EditNotificationView {

    override val layoutId = R.layout.fragment_edit_notification

    companion object {
        const val BUNDLE_NOTIFICATION_INFO = "BundleNotificationInfo"
    }
}