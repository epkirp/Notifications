package ru.webant.notifications.utils

private const val MILLISECONDS_IN_ONE_MINUTE = 1000L
private const val MILLISECONDS_IN_ONE_HOUR = MILLISECONDS_IN_ONE_MINUTE * 60

fun convertHoursAndMinutesToLong(hourOfDay: Int, minute: Int): Long {
    return MILLISECONDS_IN_ONE_HOUR * hourOfDay + MILLISECONDS_IN_ONE_MINUTE * minute
}

fun convertLongToMinutes(milliseconds: Long): Int {
    return (milliseconds / MILLISECONDS_IN_ONE_MINUTE).toInt()
}

fun convertLongToHours(milliseconds: Long): Int {
    return (milliseconds / MILLISECONDS_IN_ONE_HOUR).toInt()
}

fun convertLongToMinutesLessHour(milliseconds: Long): Int {
    val hours = convertLongToHours(milliseconds)
    return convertLongToMinutes(milliseconds - hours * MILLISECONDS_IN_ONE_HOUR)
}

fun convertLongToString(milliseconds: Long): String {
    val hourOfDay = convertLongToHours(milliseconds)
    val minute = convertLongToMinutesLessHour(milliseconds)

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

    return "$stringHour:$stringMinute"
}