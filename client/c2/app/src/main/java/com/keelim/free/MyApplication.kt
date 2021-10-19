package com.keelim.free

import android.app.Application
import com.keelim.free.notification.NotificationChannels
import com.keelim.free.util.ThemeManager
import com.keelim.free.util.ThemeType
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application(){
    @Inject lateinit var themeManager: ThemeManager

    override fun onCreate() {
        super.onCreate()
        themeManager.applyTheme(ThemeType.DEFAULT)
        NotificationChannels.init(this)
    }
}