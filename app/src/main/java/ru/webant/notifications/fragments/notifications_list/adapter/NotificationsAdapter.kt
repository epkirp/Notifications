package ru.webant.notifications.fragments.notifications_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_notification.view.*
import ru.webant.domain.NotificationEntity
import ru.webant.notifications.R

class NotificationsAdapter : RecyclerView.Adapter<NotificationsAdapter.NotificationHolder>() {

    lateinit var callback: Callback
    private val notifications = ArrayList<NotificationEntity>()

    interface Callback {
        fun onNotificationClick(notification: NotificationEntity)
        fun onNotificationSwitched(notification: NotificationEntity, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_notification, parent, false)
        return NotificationHolder(view)
    }

    override fun getItemCount(): Int = notifications.size

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.onBind(notifications[position])
    }

    fun updateNotificationsList(notifications: List<NotificationEntity>) {
        val diff =
            DiffUtil.calculateDiff(NotificationsDiffCallback(notifications, this.notifications))
        diff.dispatchUpdatesTo(this)
        this.notifications.apply {
            clear()
            addAll(notifications)
        }
    }

    fun addNotificationsList(notifications: List<NotificationEntity>) {
        val index = this.notifications.size
        this.notifications.addAll(notifications)
        notifyItemRangeInserted(index, notifications.size)
    }

    fun changeNotification(
        oldNotification: NotificationEntity,
        newNotification: NotificationEntity
    ) {
        val index = notifications.indexOf(oldNotification)
        notifications.remove(oldNotification)
        notifications.add(index, newNotification)
        notifyItemChanged(index)
    }

    inner class NotificationHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.llNotification.setOnClickListener {
                callback.onNotificationClick(notifications[adapterPosition])
                notifyItemChanged(notifications.size)
            }

            view.switchActive.setOnCheckedChangeListener { _, isChecked ->
                callback.onNotificationSwitched(notifications[adapterPosition], isChecked)
            }
        }

        fun onBind(notification: NotificationEntity) {
            with(view) {
                tvTitle.text = notification.name
                tvDescription.text =
                    context.getString(
                        R.string.notification_description,
                        notification.startTime,
                        notification.endTime,
                        notification.interval
                    )
                view.switchActive.isChecked = notification.isActive
            }
        }
    }
}