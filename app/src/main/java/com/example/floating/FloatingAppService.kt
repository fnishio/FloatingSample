package com.example.floating

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.view.WindowManager

class FloatingAppService : Service() {

    companion object {
        const val ACTION_START = "start"
        const val ACTION_STOP = "stop"
    }

    private var floatingIcon: FloatingIcon? = null
    private var floatingMenu: FloatingMenu? = null

    override fun onCreate() {
        super.onCreate()

        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        floatingIcon = FloatingIcon(this, windowManager,
                R.drawable.ic_videogame_asset_black_24dp)
        floatingMenu = FloatingMenu(this, windowManager)
    }
    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_START) {
            floatingIcon?.visible()
        } else if (intent?.action == ACTION_STOP){
            floatingIcon?.unvisible()
        }
        return START_STICKY
    }

    fun showMenu(x: Int, y: Int) {
        floatingIcon?.unvisible()
        floatingMenu?.visible(x, y)
    }

    fun closeMenu() {
        floatingMenu?.unvisible()
        floatingIcon?.visible()
    }
}