package co.si.dialogLC.util

import android.content.Context
import android.graphics.PixelFormat
import android.os.CountDownTimer
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import co.si.dialogLC.R


const val TAG = "DialogLIC"

fun disconnectCall(context: Context) {
    //TODO: correct the code, this needs to fixed
    try {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val telephonyClass = Class.forName(telephonyManager.javaClass.name)
        val telephonyMethod = telephonyClass.getDeclaredMethod("getITelephony")
        telephonyMethod.isAccessible = true
        val telephonyInterface = telephonyMethod.invoke(telephonyManager)
        val telephonyInterfaceClass = Class.forName(telephonyInterface.javaClass.name)
        val endCallMethod = telephonyInterfaceClass.getDeclaredMethod("endCall")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
fun endCall(context: Context) {
    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    try {
        var c = Class.forName(tm.javaClass.name)
        var m = c.getDeclaredMethod("getITelephony")
        m.isAccessible = true
        val telephonyService = m.invoke(tm)

        c = Class.forName(telephonyService.javaClass.name)
        m = c.getDeclaredMethod("endCall")
        m.isAccessible = true
        m.invoke(telephonyService)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

fun Context.showDialogUsingWindowManager() {

    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val layoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,

        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
        PixelFormat.TRANSLUCENT
    )

    val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view = inflater.inflate(R.layout.custom_dialog, null)
    val btnBtmLeft = view.findViewById<View>(R.id.btnBtmLeft) as Button
    val btnBtmRight = view.findViewById<View>(R.id.btnBtmRight) as Button

    btnBtmLeft.setOnClickListener { windowManager.removeView(view) }

    btnBtmRight.setOnClickListener {
        openScreenLock()
    }
    windowManager.addView(view, layoutParams)
}

fun Context.openScreenLock() {
    startCountdownTimer(15, 1)
}

fun Context.startCountdownTimer(durationInSeconds: Long, intervalInSeconds: Long) {
    Toast.makeText(this, "Countdown started!", Toast.LENGTH_SHORT).show()
    object : CountDownTimer(durationInSeconds * 1000, intervalInSeconds * 1000) {

        override fun onTick(millisUntilFinished: Long) {
            val secondsRemaining = millisUntilFinished / 1000
            Log.d(TAG, "Time left: $secondsRemaining seconds")
        }

        override fun onFinish() {
            Log.d(TAG, "Countdown finished!")
            Toast.makeText(this@startCountdownTimer, "Countdown finished!", Toast.LENGTH_SHORT)
                .show()
        }
    }.start()
}