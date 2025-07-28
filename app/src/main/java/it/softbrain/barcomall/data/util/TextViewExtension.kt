package it.softbrain.barcomall.data.util

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.widget.TextView


fun TextView.rainbowEffect() {
    val colors = listOf(Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN)
    var index = 0

    val handler = Handler(Looper.getMainLooper())
    val runnable = object : Runnable {
        override fun run() {
            setTextColor(colors[index % colors.size])
            index++
            handler.postDelayed(this, 500) // change every 500ms
        }
    }

    handler.post(runnable)
}