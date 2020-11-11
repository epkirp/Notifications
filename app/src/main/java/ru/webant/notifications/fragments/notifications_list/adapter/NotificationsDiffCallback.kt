package ru.webant.notifications.fragments.notifications_list.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.webant.domain.NotificationEntity


class NotificationsDiffCallback(
    private val oldList: List<NotificationEntity>,
    private val newList: List<NotificationEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}