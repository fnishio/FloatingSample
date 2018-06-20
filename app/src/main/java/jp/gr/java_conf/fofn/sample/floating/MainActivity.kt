package jp.gr.java_conf.fofn.sample.floating

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQ_CODE = 1
        private const val TAG = "MainActivity"
    }

    private var enabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        if (canDrawOverlays()) {
            val intent = Intent(this, FloatingAppService::class.java)
                    .setAction(FloatingAppService.ACTION_STOP)
            startService(intent)
        } else {
            requestPermission()
        }
    }

    override fun onStop() {
        super.onStop()
        if (enabled && canDrawOverlays()) {
            val intent = Intent(this, FloatingAppService::class.java)
                    .setAction(FloatingAppService.ACTION_START)
            startService(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQ_CODE -> Log.d(TAG, "enable overlay permission")
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        enabled = false
        return super.onTouchEvent(event)
    }

    private fun canDrawOverlays() : Boolean {
        return if (Build.VERSION.SDK_INT >= 23) Settings.canDrawOverlays(this) else true
    }


    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, REQ_CODE)
        }
    }
}
