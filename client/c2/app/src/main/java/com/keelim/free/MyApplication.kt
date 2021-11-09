package com.keelim.free

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.keelim.free.notification.NotificationChannels
import com.keelim.free.util.ComponentLogger
import com.keelim.free.util.ThemeManager
import com.keelim.free.util.ThemeType
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {
    @Inject
    lateinit var themeManager: ThemeManager

    @Inject
    lateinit var componentLogger: ComponentLogger

    override fun onCreate() {
        super.onCreate()
        themeManager.applyTheme(ThemeType.DEFAULT)
        NotificationChannels.init(this)
        componentLogger.initialize(this)
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}