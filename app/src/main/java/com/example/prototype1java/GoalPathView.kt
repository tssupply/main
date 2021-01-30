// evaluation prototype code WIP
package com.example.prototype1java

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.max

class GoalPathView : View {
    private var path: Path? = null
    private var paint: Paint? = null
    private var length = 0f
    private var rect2: RectF? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun init() {
        setGoal()
        paint = Paint()
        setupLinePath()
        val measure = PathMeasure(path, false)
        length = measure.length
        annimateScores()
    }

    private fun setGoal() {
        rect2 = RectF(480f, 370f, 1050f, 590f)
    }

    private fun annimateScores() {
        ObjectAnimator.ofFloat(this@GoalPathView, "phase", 1.0f, 0.0f)
                .setDuration(8000)
                .start()
        ObjectAnimator.ofObject(this@GoalPathView, "color", ArgbEvaluator(), -0x100, Color.YELLOW, Color.YELLOW, Color.rgb(255, 165, 0))
                .setDuration(8000)
                .start()
    }

    private fun setupLinePath() {
        paint!!.color = Color.TRANSPARENT
        paint!!.strokeWidth = 5f
        paint!!.style = Paint.Style.STROKE
        path = Path()
        path!!.moveTo(0f, 0f)
        path!!.lineTo(850f, 1200f)
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
        rect2?.let { c.drawOval(rect2!!, paint!!)}
    }

    companion object {
        private fun createPathEffect(pathLength: Float, phase: Float, offset: Float): PathEffect {
            return DashPathEffect(floatArrayOf(pathLength, pathLength),
                    max(phase * pathLength, offset))
        }
    }
}