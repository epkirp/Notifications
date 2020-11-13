package ru.webant.notifications.fragments.edit_notification

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.webant.notifications.App
import ru.webant.notifications.R
import ru.webant.notifications.fragments.base.BaseFragment

class EditNotificationFragment : BaseFragment(), EditNotificationView {

    override val layoutId = R.layout.fragment_edit_notification

    @InjectPresenter
    lateinit var presenter: EditNotificationPresenter

    @ProvidePresenter
    fun providePresenter() = App.appComponent.provideEditNotificationPresenter()

    companion object {
        const val BUNDLE_NOTIFICATION_INFO = "BundleNotificationInfo"
    }
}