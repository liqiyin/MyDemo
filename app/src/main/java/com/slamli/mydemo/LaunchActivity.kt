package com.slamli.mydemo

/**
 * Created by slam.li on 2017/8/11.
 */
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import com.slamli.mydemo.surface.SurfaceViewActivity

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainLayout:LinearLayout = findViewById(R.id.layout_main) as LinearLayout
        mainLayout.addView(getButton("surfaceView", SurfaceViewActivity::class.java))
        mainLayout.addView(getButton("audio", AudioActivity::class.java))
        mainLayout.addView(getButton("stage", StageLayoutManagerActivity::class.java))
        mainLayout.addView(getButton("webactivity", WebViewActivity::class.java))
        mainLayout.addView(getButton("fragmentdemo", FragmentActivityDemo::class.java))
    }

    fun getButton(name: String, cls: Class<out AppCompatActivity>): Button {
        val btn = Button(this)
        btn.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        btn.text = name
        btn.setOnClickListener {startActivity(Intent(this, cls))}
        return btn
    }
}