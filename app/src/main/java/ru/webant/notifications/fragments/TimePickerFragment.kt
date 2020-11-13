package ru.webant.notifications.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import ru.webant.notifications.utils.convertLongToHours
import ru.webant.notifications.utils.convertLongToMinutesLessHour

class TimePickerFragment : MvpAppCompatDialogFragment(), TimePickerDialog.OnTimeSetListener {

    lateinit var callback: Callback

    interface Callback {
        fun onTimeChosen(hourOfDay: Int, minute: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle: Bundle? = this.arguments
        val initialMilliseconds = bundle?.getLong(BUNDLE_INITIAL_TIME) as Long

        val hourOfDay = convertLongToHours(initialMilliseconds)
        val minute = convertLongToMinutesLessHour(initialMilliseconds)
        return TimePickerDialog(activity, this, hourOfDay, minute, true)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        callback.onTimeChosen(hourOfDay, minute)
    }

    companion object {
        const val BUNDLE_INITIAL_TIME = "BundleInitialTime"
        const val TIME_PICKER_DIALOG_TAG = "TimePickerDialogTag"
    }
}