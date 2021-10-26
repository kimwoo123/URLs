package com.keelim.core.notification

import android.graphics.Movie

interface NotificationBuilder {
    fun showLegacyNotification(list: List<Any>)
    fun showAlarmNotification(list: List<Any>)
}