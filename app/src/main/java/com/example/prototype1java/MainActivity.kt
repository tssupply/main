package com.example.prototype1java

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var simpleVideoView: VideoView
    private lateinit var mediaControls: MediaController
    private lateinit var score: TextView
    private lateinit var path: PathView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_15inchland)

        path = findViewById(R.id.path)
        path.init()

        setupVideo()

        val animator = setUpCounter()
        animator.duration = 6000
        animator.start()
    }

    private fun setUpCounter(): ValueAnimator {
        score = findViewById(R.id.textinput_counter)
        val animator = ValueAnimator()
        animator.setObjectValues(0, 579)
        animator.addUpdateListener { animation: ValueAnimator -> score.text = animation.animatedValue.toString() }
        animator.setEvaluator(TypeEvaluator { fraction: Float, startValue: Int, endValue: Int -> (startValue + (endValue - startValue) * fraction).roundToInt() } as TypeEvaluator<Int>)
        return animator
    }

    // evaluation prototype code WIP
    private fun setupVideo() {
        simpleVideoView = findViewById(R.id.simpleVideoView)
        simpleVideoView.setOnPreparedListener{ mp: MediaPlayer -> mp.isLooping = true }
        mediaControls = MediaController(this@MainActivity)
        mediaControls.setAnchorView(simpleVideoView)
        simpleVideoView.setMediaController(mediaControls)
        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.w1))
        simpleVideoView.start()
        simpleVideoView.setOnCompletionListener { mp: MediaPlayer? ->
            // reserved
        }
        simpleVideoView.setOnErrorListener{ mp: MediaPlayer?, what: Int, extra: Int -> false }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}