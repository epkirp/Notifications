package ru.webant.notifications.utils

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import ru.webant.notifications.fragments.TimePickerFragment

fun openTimePickerFragment(
    fragmentManager: FragmentManager,
    initialTime: Long,
    action: (hourOfDay: Int, minute: Int) -> Unit
) {
    val timePickerFragment = TimePickerFragment()
    val args = Bundle()
    args.putLong(TimePickerFragment.BUNDLE_INITIAL_TIME, initialTime)
    timePickerFragment.arguments = args

    timePickerFragment.callback = object : TimePickerFragment.Callback {
        override fun onTimeChosen(hourOfDay: Int, minute: Int) {
            action.invoke(hourOfDay, minute)
        }
    }

    timePickerFragment.show(fragmentManager, TimePickerFragment.TIME_PICKER_DIALOG_TAG)
}