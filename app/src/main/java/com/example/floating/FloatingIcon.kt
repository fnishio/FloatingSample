package com.example.floating

import android.graphics.PixelFormat
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.ImageView

class FloatingIcon(service: FloatingAppService, private val windowManager: WindowManager, id: Int)
    : ImageView(service) {
    private val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)
    private var visible: Boolean = false

    private var mInitialX = 0
    private var mInitialY = 0
    private var mTouchX = 0.0f
    private var mTouchY = 0.0f

    init {
        // set icon image
        this.setImageResource(id)

        // set initial position
        params.gravity = Gravity.TOP or Gravity.START
        params.x = 0
        params.y = 200

        // set listener
        this.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    mInitialX = params.x
                    mInitialY = params.y
                    mTouchX = event.rawX
                    mTouchY = event.rawY
                    true
                }
                MotionEvent.ACTION_UP -> {
                    // detect click
                    var diffX = event.rawX - mTouchX
                    var diffY = event.rawY - mTouchY
                    if (-5 < diffX && diffX < 5 &&
                            -5 < diffY && diffY < 5) {
                        performClick()
                    }
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    params.x = mInitialX + (event.rawX - mTouchX).toInt()
                    params.y = mInitialY + (event.rawY - mTouchY).toInt()
                    windowManager.updateViewLayout(this, params)
                    true
                }
                else -> { false}
            }
        }

        this.setOnClickListener {
            service.showMenu(params.x, params.y)
        }
    }

    override fun performClick(): Boolean {
        Log.d("floating", "clicked")
        return super.performClick()
    }

    fun visible() {
        if (!visible) {
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