package ru.webant.notifications.fragments.add_notification

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.webant.notifications.fragments.base.BaseView

interface AddNotificationView : BaseView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openStartTimePickerFragment(initialTime: Long)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openEndTimePickerFragment(initialTime: Long)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showStartTime(startTimeText: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEndTime(endTimeText: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSuccessfulDialog(messageId: Int)
}