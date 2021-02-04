// evaluation prototype code WIP
package com.example.prototype1java

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.max

class ScorePathView : View, IGoalPathView {
    override var path: Path? = null
    override var paint: Paint? = null
    override var length = 0f
    override var rectangle: RectF? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setGoal(rect: RectF) {
        rectangle = rect //RectF(580.0f, 400f, 950f, 540f)
    }

    override fun animateDraw() {
        ObjectAnimator.ofFloat(this@ScorePathView, "phase", 1.0f, 0.0f)
                .setDuration(8000)
                .start()
        ObjectAnimator.ofObject(this@ScorePathView, "color", ArgbEvaluator(), Color.WHITE, Color.WHITE, Color.WHITE)
                .setDuration(8000)
                .start()
    }

    override fun setPhase(phase: Float) {
        paint!!.pathEffect = createPathEffect(length, phase, 0.0f)
        invalidate()
    }

    override fun setColor(color: Int) {
        paint!!.color = color
        invalidate()
    }

    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        rectangle?.let { c.drawOval(rectangle!!, paint!!)}
    }

    companion object {
        private fun createPathEffect(pathLength: Float, phase: Float, offset: Float): PathEffect {
            return DashPathEffect(floatArrayOf(pathLength, pathLength),
                    max(phase * pathLength, offset))
        }
    }
}