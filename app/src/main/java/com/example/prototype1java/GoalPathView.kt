// evaluation prototype code WIP
package com.example.prototype1java

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.max

class GoalPathView : View, IGoalPathView {
    override var path: Path? = null
    override var paint: Paint? = null
    override var length = 0f
    override var rectangle: RectF? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun setGoal(rect: RectF) {
        rectangle = rect //RectF(480f, 370f, 1050f, 590f)
    }

    override fun animateDraw() {
        ObjectAnimator.ofFloat(this@GoalPathView, "phase", 1.0f, 0.0f)
                .setDuration(8000)
                .start()
        ObjectAnimator.ofObject(this@GoalPathView, "color", ArgbEvaluator(), -0x100, Color.YELLOW, Color.YELLOW, Color.rgb(255, 165, 0))
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
        fun createPathEffect(pathLength: Float, phase: Float, offset: Float): PathEffect {
            return DashPathEffect(floatArrayOf(pathLength, pathLength),
                    max(phase * pathLength, offset))
        }
    }
}