
package com.keelim.free.notification;

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Movie
import androidx.appcompat.resources.Compatibility.Api18Impl.setAutoCancel
import androidx.core.app.NotificationCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.keelim.core.extensions.getColorCompat
import com.keelim.core.notification.NotificationBuilder
import com.keelim.free.R
import com.keelim.free.ui.main.MainActivity

class NotificationBuilderImpl(context: Context) : NotificationBuilder {

    private val applicationContext = context.applicationContext

    override fun showLegacyNotification(list: List<Any>) = applicationContext.run {
        NotificationSpecs.notifyLegacy(this) {
            setStyle(NotificationCompat.BigTextStyle())
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(buildSpannedString { bold { append("This is Free") } })
            setContentText(list.joinToString { it.toString() })
            setAutoCancel(true)
            setContentIntent(createLauncherIntent())
//            setColor(getColorCompat(R.color.colorSecondary))
        }
    }

    override fun showAlarmNotification(list: List<Any>) = applicationContext.run {
//        NotificationSpecs.notifyOpenDateAlarm(this) {
//            setStyle(NotificationCompat.BigTextStyle())
//            setSmallIcon(R.mipmap.ic_launcher)
//            setContentTitle(buildSpannedString { bold { append("This is Free") } })
//            setContentText(list.joinToString { it.toString() })
//            setAutoCancel(true)
//            setContentIntent(createLauncherIntent())
//            setColor(getColorCompat(R.color.colorSecondary))
//        }
    }

    private fun Context.createLauncherIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
    }
}