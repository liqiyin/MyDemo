package com.slamli.mydemo.surface

import android.content.Context
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView


/**
 * Created by slam.li on 2017/8/15.
 */
class MySurfaceView: SurfaceView, SurfaceHolder.Callback, Runnable {
    var isRunning = true
    val paint = Paint()

    constructor(context: Context?) : super(context) {
        holder.addCallback(this)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        paint.color = context?.resources?.getColor(android.R.color.white) as Int
        paint.textSize = 24F
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        isRunning = false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Thread(this).start()
    }

    override fun run() {
        while(isRunning) {
            draw()
            Thread.sleep(500)
        }
    }

    fun draw() {
        val text = System.currentTimeMillis()
        val canvas = holder.lockCanvas()
        if (canvas != null) {
            try {
                //clear canvas
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                canvas.drawPaint(paint)
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
                canvas.drawText(text.toString(), (width / 2).toFloat(), (height / 2).toFloat(), paint)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                holder.unlockCanvasAndPost(canvas)
            }
        }
    }

}