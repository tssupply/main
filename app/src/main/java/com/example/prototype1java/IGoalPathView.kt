package com.example.prototype1java

import android.graphics.*

interface IGoalPathView {
    var path: Path?
    var paint: Paint?
    var length: Float
    var rectangle: RectF?
    fun setGoal(rect: RectF)
    fun animateDraw()
    fun setPhase(phase: Float)
    fun setColor(color: Int)
}

fun IGoalPathView.initPath(rect: RectF) {
    setGoal(rect)
    paint = Paint()
    setupLinePath()
    val measure = PathMeasure(path, false)
    length = measure.length
    animateDraw()
}

fun IGoalPathView.setupLinePath() {
    paint!!.color = Color.TRANSPARENT
    paint!!.strokeWidth = 4f
    paint!!.style = Paint.Style.STROKE
    path = Path()
    path!!.moveTo(0f, 0f)
    path!!.lineTo(850f, 1200f)
}

