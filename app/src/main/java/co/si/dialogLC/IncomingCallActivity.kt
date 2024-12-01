package co.si.dialogLC

import android.os.Bundle
import android.os.Handler
import android.telephony.TelephonyManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import co.si.dialogLC.util.TAG
import co.si.dialogLC.util.endCall
import co.si.dialogLC.util.openScreenLock


class IncomingCallActivity : AppCompatActivity() {
    var mCurrentX: Int = 0
    var mCurrentY: Int = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
//        showDialogUsingWindowManager()
        openPopUp()
    }


    private fun openPopUp(){
        try {
            val layoutInflater =
                getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val popupView: View = layoutInflater.inflate(R.layout.custom_dialog, null)

            val buttonDismiss = popupView.findViewById<Button>(R.id.btnBtmLeft)
            val buttonAction = popupView.findViewById<Button>(R.id.btnBtmRight)
            val text = popupView.findViewById<TextView>(R.id.txtNumber)


            val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            Handler().postDelayed(Runnable {
                popupWindow.showAtLocation(popupView, Gravity.NO_GRAVITY, mCurrentX, mCurrentY)
                popupView.bringToFront()
            }, 100)

            popupView.bringToFront()
            buttonDismiss.setOnClickListener {
                popupWindow.dismiss()
                finish()
            }
            buttonAction.setOnClickListener {
//                openScreenLock()
                endCall(this)
                popupWindow.dismiss()
                finish()
            }

            popupView.setOnTouchListener(object : OnTouchListener {
                var orgX: Int = 0
                var orgY: Int = 0
                var offsetX: Int = 0
                var offsetY: Int = 0

                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            orgX = (mCurrentX - event.rawX).toInt()
                            orgY = (mCurrentY - event.rawY).toInt()
                        }

                        MotionEvent.ACTION_MOVE -> {
                            mCurrentX = event.rawX.toInt() + orgX
                            mCurrentY = event.rawY.toInt() + orgY
                            popupWindow.update(mCurrentX, mCurrentY, -1, -1, true)
                        }
                    }
                    return true
                }
            })

            val number = intent.getStringExtra(
                TelephonyManager.EXTRA_INCOMING_NUMBER
            )
            text.text = "Calling $number ..."
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
            e.printStackTrace()
        }
    }
}
