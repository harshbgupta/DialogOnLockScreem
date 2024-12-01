package co.si.dialogLC

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val permission_draw_over_other_app = 2084
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            checkDrawOverOtherAppPermission()
        }
    }

    private fun checkDrawOverOtherAppPermission() {
        if (!Settings.canDrawOverlays(this)) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, permission_draw_over_other_app)
        } else {
            Toast.makeText(
                this,
                "Permission already granted, Draw over other app.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == permission_draw_over_other_app) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                Toast.makeText(
                    this,
                    "Permission granted, Draw over other app.",
                    Toast.LENGTH_SHORT
                ).show()
            } else { //Permission is not available
                Toast.makeText(
                    this,
                    "Draw over other app permission not available",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}