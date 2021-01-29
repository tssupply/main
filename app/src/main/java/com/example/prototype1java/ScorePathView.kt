// evaluation prototype code WIP
package com.example.prototype1java

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.max

class ScorePathView : View {
    private var path: Path? = null
    private var paint: Paint? = null
    private var length = 0f
    private var rect: RectF? = null
    private var rect2: RectF? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun init() {
        setScores()
        paint = Paint()
        setupLinePath()

        val measure = PathMeasure(path, false)
        length = measure.length

        annimateScores()

    }

    private fun setScores() {
        //float[] intervals = new float[]{length, length};
        rect = RectF(580.0f, 400f, 950f, 540f)
        rect2 = RectF(480f, 380f, 1050f, 600f)
    }

    private fun annimateScores() {
        ObjectAnimator.ofFloat(this@ScorePathView, "phase", 1.0f, 0.0f)
                .setDuration(8000)
                .start()
        ObjectAnimator.ofObject(this@ScorePathView, "color", ArgbEvaluator(), -0x100, -0xff0100, -0x5b00)
                .setDuration(8000)
                .start()
    }

    private fun setupLinePath() {
        paint!!.color = Color.TRANSPARENT
        paint!!.strokeWidth = 4f
        paint!!.style = Paint.Style.STROKE
        path = Path()
        path!!.moveTo(50f, 50f)
        path!!.lineTo(50f, 500f)
        path!!.lineTo(200f, 500f)
        path!!.lineTo(200f, 300f)
        path!!.lineTo(350f, 300f)
    }

    fun setPhase(phase: Float) {
        paint!!.pathEffect = createPathEffect(length, phase, 0.0f)
        invalidate()
    }

    fun setColor(color: Int) {
        paint!!.color = color
        invalidate()
    }

    public override fun onDraw(c: Canvas) {
        super.onDraw(c)
        //c.drawPath(path, paint);
        if(rect != null) c.drawOval(rect!!, paint!!)
        if(rect2 != null) c.drawOval(rect2!!, paint!!)
    }

    companion object {
        private fun createPathEffect(pathLength: Float, phase: Float, offset: Float): PathEffect {
            return DashPathEffect(floatArrayOf(pathLength, pathLength),
                    max(phase * pathLength, offset))
        }
    }
}