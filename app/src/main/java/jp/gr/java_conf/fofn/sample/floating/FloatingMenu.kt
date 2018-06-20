package jp.gr.java_conf.fofn.sample.floating

import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ListView

class FloatingMenu(service: FloatingAppService, private val windowManager: WindowManager)
    : ListView(service) {
    private val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)
    private var visible = false

    init {
        var list = arrayListOf("Game Focus Mode", "Search", "Live & Record", "Screen shot", "Share")
        var adapter = ArrayAdapter<String>(context, R.layout.menu_item, list)
        this.adapter = adapter

        // set initial position
        params.gravity = Gravity.TOP or Gravity.START
        params.x = 0
        params.y = 200
        params.width = 500

        this.setOnItemClickListener { parent, view, position, id ->
            service.closeMenu()
        }
    }

    fun visible(x: Int, y: Int) {
        if (!visible) {
            params.x = x.toInt() + 10
            params.y = y.toInt()
            windowManager.addView(this, params)
            visible = true
        }
    }

    fun unvisible() {
        if (visible) {
            windowManager.removeView(this)
            visible = false
        }
    }
}