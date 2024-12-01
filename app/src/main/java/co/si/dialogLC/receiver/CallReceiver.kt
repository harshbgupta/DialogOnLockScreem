package co.si.dialogLC.receiver

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import co.si.dialogLC.IncomingCallActivity
import co.si.dialogLC.util.TAG
import co.si.dialogLC.util.showDialogUsingWindowManager
import java.util.Date


class CallReceiver : PhoneCallReceiver() {

    override fun onIncomingCallStarted(ctx: Context?, number: String?, start: Date?) {
        Toast.makeText(ctx, "Incoming call started", Toast.LENGTH_SHORT).show()
        if (receiverContext != null) {
            Handler().postDelayed({
                val intent = Intent(receiverContext, IncomingCallActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
                intent.putExtra(TelephonyManager.EXTRA_INCOMING_NUMBER, number)
                receiverContext!!.startActivity(intent)
//
//                receiverContext!!.showDialogUsingWindowManager()
            }, 2000)
        } else {
            Log.d(TAG, "receiverContext is null -> onIncomingCallStarted")
        }
    }

    override fun onOutgoingCallStarted(ctx: Context?, number: String?, start: Date?) {
        Log.d(TAG, "Outgoing call started")
        Toast.makeText(ctx, "Outgoing call started", Toast.LENGTH_SHORT).show()
    }

    override fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
        Log.d(TAG, "Incoming call ended")
        Toast.makeText(ctx, "Incoming call ended", Toast.LENGTH_SHORT).show()
    }

    override fun onOutgoingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
        Log.d(TAG, "Outgoing call ended")
        Toast.makeText(ctx, "Outgoing call ended", Toast.LENGTH_SHORT).show()
    }

    override fun onMissedCall(ctx: Context?, number: String?, start: Date?) {
        Log.d(TAG, "Incoming call missed")
        Toast.makeText(ctx, "Incoming call missed", Toast.LENGTH_SHORT).show()
    }
}