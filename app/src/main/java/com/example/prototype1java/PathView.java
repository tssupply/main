// evaluation prototype code WIP
package com.example.prototype1java;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.util.Log;

public class PathView extends View
{
    Path path;
    Paint paint;
    float length;
    RectF rect, rect2;

    public PathView(Context context)
    {
        super(context);
    }

    public PathView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void init()
    {
        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        path.moveTo(50, 50);
        path.lineTo(50, 500);
        path.lineTo(200, 500);
        path.lineTo(200, 300);
        path.lineTo(350, 300);

        // Measure the path
        PathMeasure measure = new PathMeasure(path, false);
        length = measure.getLength();

        //float[] intervals = new float[]{length, length};

        ObjectAnimator.ofFloat(PathView.this, "phase", 1.0f, 0.0f)
                .setDuration(8000)
                .start();

        ObjectAnimator.ofObject(PathView.this, "color", new ArgbEvaluator(), 0xFFFFFF00, 0xFF00FF00, 0xFFFFA500)
                .setDuration(8000)
                .start();

        rect = new RectF(580,400,950,540);
        rect2 = new RectF(480,380,1050,600);
    }

    public void setPhase(float phase)
    {
        paint.setPathEffect(createPathEffect(length, phase, 0.0f));
        invalidate();
    }
    public void setColor(int color) {
        paint.setColor(color);
        invalidate();
    }
    private static PathEffect createPathEffect(float pathLength, float phase, float offset)
    {
        return new DashPathEffect(new float[] { pathLength, pathLength },
                Math.max(phase * pathLength, offset));
    }

    @Override
    public void onDraw(Canvas c)
    {
        super.onDraw(c);
        //c.drawPath(path, paint);
        c.drawOval(rect,paint);
        c.drawOval(rect2,paint);
    }
}