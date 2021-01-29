package com.example.prototype1java

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private lateinit var simpleVideoView: VideoView
    private lateinit var mediaControls: MediaController
    private lateinit var scoreView: TextView
    private lateinit var scorePathView: ScorePathView
    private lateinit var btoggleButton1: ToggleImageButton
    private var selectedToggle = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_15inchland)

        setUpPath()
        setupVideo()
        setupScoreAnimation()
        setupToggles()
    }

    private fun setupToggles() {
        btoggleButton1 = findViewById(R.id.bored_button)
        val clickListener = (object : ToggleImageButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: ToggleImageButton?, isChecked: Boolean) {
                if (buttonView != null && isChecked) {
                    selectedToggle = buttonView.id
                    if (selectedToggle != R.id.bored_button) (findViewById<ToggleImageButton>(R.id.bored_button)).isChecked = false
                    if (selectedToggle != R.id.neutral_button) (findViewById<ToggleImageButton>(R.id.neutral_button)).isChecked = false
                    if (selectedToggle != R.id.amazing_button) (findViewById<ToggleImageButton>(R.id.amazing_button)).isChecked = false
                    if (selectedToggle != R.id.discourage_button) (findViewById<ToggleImageButton>(R.id.discourage_button)).isChecked = false
                    if (selectedToggle != R.id.motivated_button) (findViewById<ToggleImageButton>(R.id.motivated_button)).isChecked = false
                }
            }
        })
        (findViewById<ToggleImageButton>(R.id.bored_button)).onCheckedChangeListener = clickListener
        (findViewById<ToggleImageButton>(R.id.neutral_button)).onCheckedChangeListener = clickListener
        (findViewById<ToggleImageButton>(R.id.amazing_button)).onCheckedChangeListener = clickListener
        (findViewById<ToggleImageButton>(R.id.discourage_button)).onCheckedChangeListener = clickListener
        (findViewById<ToggleImageButton>(R.id.motivated_button)).onCheckedChangeListener = clickListener
    }

    private fun setupScoreAnimation() {
        val animator = setUpCounter()
        animator.duration = 6000
        animator.start()
    }

    private fun setUpPath() {
        scorePathView = findViewById(R.id.path)
        scorePathView.init()
    }

    private fun setUpCounter(): ValueAnimator {
        scoreView = findViewById(R.id.textinput_counter)
        val animator = ValueAnimator()
        animator.setObjectValues(0, 579)
        animator.addUpdateListener { animation: ValueAnimator -> scoreView.text = animation.animatedValue.toString() }
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
        simpleVideoView.setOnCompletionListener { mp: MediaPlayer? ->  // reserved
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