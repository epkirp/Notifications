package ru.webant.notifications.fragments.notifications_list

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.layout_loader.*
import kotlinx.android.synthetic.main.layout_placeholder.*
import ru.webant.domain.NotificationEntity
import ru.webant.notifications.App
import ru.webant.notifications.R
import ru.webant.notifications.fragments.add_notification.AddNotificationFragment
import ru.webant.notifications.fragments.base.BaseFragment
import ru.webant.notifications.fragments.edit_notification.EditNotificationFragment
import ru.webant.notifications.fragments.edit_notification.EditNotificationFragment.Companion.BUNDLE_NOTIFICATION_INFO
import ru.webant.notifications.fragments.notifications_list.adapter.NotificationsAdapter
import ru.webant.notifications.utils.changeVisibilityState

class NotificationsFragment : BaseFragment(), NotificationsView {

    private lateinit var adapter: NotificationsAdapter
    private lateinit var placeholder: View
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var loader: View
    private lateinit var paginationLoader: ProgressBar

    override val layoutId = R.layout.fragment_notifications

    @InjectPresenter
    lateinit var presenter: NotificationsPresenter

    @ProvidePresenter
    fun providePresenter() = App.appComponent.provideNotificationsPresenter()

    override fun setUpUI() {
        super.setUpUI()

        placeholder = llNoNotifications
        refreshLayout = srlNotifications
        loader = llLoading
        paginationLoader = pbNotifications
    }

    override fun setUpListeners() {
        super.setUpListeners()

        refreshLayout.setOnRefreshListener {
            presenter.onSwipeToRefresh()
        }

        ibAdd.setOnClickListener {
            presenter.onAddNotification()
        }
    }

    override fun openEditNotificationFragment(notification: NotificationEntity) {
        val editNotificationFragment = EditNotificationFragment()
        val args = Bundle()
        args.putSerializable(BUNDLE_NOTIFICATION_INFO, notification)
        editNotificationFragment.arguments = args

        editNotificationFragment.setTargetFragment(this, EDIT_NOTIFICATION_REQUEST_CODE)

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.addToBackStack(null)
            ?.add(R.id.container, editNotificationFragment)
            ?.commit()
    }

    override fun openAddNotificationFragment() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.addToBackStack(null)
            ?.add(R.id.container, AddNotificationFragment())
            ?.commit()
    }

    override fun showNewItems(items: List<NotificationEntity>) {
        adapter.addNotificationsList(items)
    }

    override fun showInitialItems(items: ArrayList<NotificationEntity>) {
        callbackAdapter()
        adapter.updateNotificationsList(items)
        rvNotifications.adapter = adapter
        val layoutManager = rvNotifications.layoutManager as LinearLayoutManager
        addOnScrollListenerRecyclerView(rvNotifications, layoutManager) {
            presenter.onNewPageScrolled()
        }
    }

    private fun callbackAdapter() {
        adapter = NotificationsAdapter()
        adapter.callback = object : NotificationsAdapter.Callback {

            override fun onNotificationClick(notification: NotificationEntity) {
                presenter.onNotificationClick(notification)
            }

            override fun onNotificationSwitched(
                notification: NotificationEntity,
                isChecked: Boolean
            ) {
                presenter.onNotificationSwitched(notification, isChecked)
            }
        }
    }

    private fun addOnScrollListenerRecyclerView(
        recyclerView: RecyclerView,
        layoutManager: LinearLayoutManager,
        action: () -> Unit
    ) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastVisibleItemPosition() >= recyclerView.adapter!!.itemCount - 2) {
                    action.invoke()
                }
            }
        })
    }

    override fun changePlaceholderVisibilityState(isVisible: Boolean) {
        placeholder.changeVisibilityState(isVisible)
    }

    override fun changeLoaderVisibilityState(isVisible: Boolean) {
        loader.changeVisibilityState(isVisible)
    }

    override fun changeRefreshVisibilityState(isVisible: Boolean) {
        refreshLayout.isRefreshing = isVisible
    }

    override fun changePaginationLoaderState(isVisible: Boolean) {
        paginationLoader.changeVisibilityState(isVisible)
    }

    companion object {
        const val EDIT_NOTIFICATION_REQUEST_CODE = 1010
    }
}