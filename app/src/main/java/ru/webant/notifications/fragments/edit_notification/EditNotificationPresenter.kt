package ru.webant.notifications.fragments.edit_notification

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.webant.domain.Action
import ru.webant.domain.NotificationEntity
import ru.webant.domain.gateway.NotificationsGateway
import ru.webant.notifications.R
import ru.webant.notifications.fragments.base.BasePresenter
import ru.webant.notifications.utils.convertHoursAndMinutesToLong
import ru.webant.notifications.utils.convertLongToString
import java.util.*
import javax.inject.Inject

@InjectViewState
class EditNotificationPresenter @Inject constructor(
    private val notificationsGateway: NotificationsGateway
) : BasePresenter<EditNotificationView>() {

    private var actionsList = Action.getAllActions()
    private var id = -1
    private var name = ""
    private var startTime = 0L
    private var endTime = 0L
    private var interval = 0L
    private var isActive = false

    fun getActionsList() = actionsList

    fun onActionChanged(position: Int) {
        name = actionsList[position]
    }

    fun onFillNotificationData(notification: NotificationEntity) {
        id = notification.id
        name = notification.name
        startTime = notification.startTime
        endTime = notification.endTime
        interval = notification.interval
        isActive = notification.isActive

        val actionPosition = when (Action.stringActionToAction(name)) {
            Action.DefaultAction -> 0
            Action.DrinkWater -> 1
            Action.DoExercises -> 2
        }

        viewState.showInitialData(
            startTime = convertLongToString(startTime),
            endTime = convertLongToString(endTime),
            interval = interval.toString(),
            actionPosition = actionPosition
        )
    }


    fun onStartTimeClicked() {
        val initialTime = if (startTime == 0L) {
            Calendar.getInstance().time.time
        } else {
            startTime
        }
        viewState.openStartTimePickerFragment(initialTime)
    }

    fun onEndTimeClicked() {
        val initialTime = if (endTime == 0L) {
            Calendar.getInstance().time.time
        } else {
            endTime
        }

        viewState.openEndTimePickerFragment(initialTime)
    }

    fun onStartTimeChanged(hourOfDay: Int, minute: Int) {
        val stringHour = if (hourOfDay % 10 == 0) {
            "0$hourOfDay"
        } else {
            hourOfDay.toString()
        }

        val stringMinute = if (minute % 10 == 0) {
            "0$minute"
        } else {
            minute.toString()
        }

        startTime = convertHoursAndMinutesToLong(hourOfDay, minute)
        viewState.showStartTime("$stringHour:$stringMinute")
    }

    fun onSaveNotificationClicked() {
        notificationsGateway.addNotification(
            NotificationEntity(
                id,
                name,
                startTime,
                endTime,
                interval,
                isActive
            )
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showSuccessfulDialog(R.string.successful_creating)
            }, {
                it.printStackTrace()
            })
            .let(compositeDisposable::add)
    }

    fun onEndTimeChanged(hourOfDay: Int, minute: Int) {
        val stringHour = if (hourOfDay % 10 == 0) {
            "0$hourOfDay"
        } else {
            hourOfDay.toString()
        }

        val stringMinute = if (minute % 10 == 0) {
            "0$minute"
        } else {
            minute.toString()
        }

        endTime = convertHoursAndMinutesToLong(hourOfDay, minute)
        viewState.showEndTime("$stringHour:$stringMinute")
    }

    fun onRepeatTimeChanged(text: CharSequence?) {
        interval = text?.toString()?.toInt()?.toLong() ?: 0
    }
}