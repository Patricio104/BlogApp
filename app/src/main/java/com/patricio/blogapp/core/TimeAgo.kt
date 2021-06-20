package com.patricio.blogapp.core

import java.util.concurrent.TimeUnit

private const val SECOND_MILLS = 1
private const val MINUTE_MILLS = 60 * SECOND_MILLS
private const val HOUR_MILLS = 60 * MINUTE_MILLS
private const val DAY_MILLS = 24 * HOUR_MILLS
object TimeUtils {
    fun getTimeAgo(time: Int): String{
        val now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        if (time>now || time <= 0){
            return "in the future"
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLS -> "Just now"
            diff < 2 * MINUTE_MILLS -> "A minute ago"
            diff < 60 * MINUTE_MILLS -> "${diff / MINUTE_MILLS} minutes ago"
            diff < 2 * HOUR_MILLS -> "An hour ago"
            diff < 24 * HOUR_MILLS -> "${diff / HOUR_MILLS} hours ago"
            diff < 48 * HOUR_MILLS -> "Yesterday"
            else -> "${diff / DAY_MILLS} days ago"
        }
    }
}