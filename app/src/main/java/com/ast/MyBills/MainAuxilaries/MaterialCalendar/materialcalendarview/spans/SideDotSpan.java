package com.ast.MyBills.MainAuxilaries.MaterialCalendar.materialcalendarview.spans;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;


/**
 * Created by usamak.mirza@gmail.com
 */


/**
 * Span to draw a dot under a section of text
 */
public class SideDotSpan implements LineBackgroundSpan {

    /**
     * Default radius used
     */
    public static final float DEFAULT_RADIUS = 5;
    private final int colorCircle;
    private final int colorStroke;
    private final float radius;

    /**
     * Create a span to draw a dot using default radius and color
     *
     * @see #SideDotSpan(int, int, float)
     * @see #DEFAULT_RADIUS
     */
    public SideDotSpan() {
        this.colorCircle = Color.RED;
        this.colorStroke = Color.WHITE;
        this.radius = DEFAULT_RADIUS;
    }

    /**
     * Create a span to draw a dot using a specified color
     *
     * @param _colorCircle color of the dot
     * @see #SideDotSpan(int, int, float)
     * @see #DEFAULT_RADIUS
     */
    public SideDotSpan(int _colorCircle) {
        this.colorCircle = _colorCircle;
        this.colorStroke = Color.WHITE;
        this.radius = DEFAULT_RADIUS;
    }

    /**
     * Create a span to draw a dot using a specified radius
     *
     * @param _radius radius for the dot
     * @see #SideDotSpan(int, int, float)
     */
    public SideDotSpan(float _radius) {
        this.colorCircle = Color.RED;
        this.colorStroke = Color.WHITE;
        this.radius = _radius;
    }

    /**
     * Create a span to draw a dot using a specified radius and color
     *
     * @param _colorCircle color of the dot
     * @param _colorStroke color of the dot stroke
     * @param _radius      radius for the dot
     */
    public SideDotSpan(int _colorCircle, int _colorStroke, float _radius) {
        this.colorCircle = _colorCircle;
        this.colorStroke = _colorStroke;
        this.radius = _radius;
    }

    @Override
    public void drawBackground(
            Canvas canvas, Paint paint,
            int left, int right, int top, int baseline, int bottom,
            CharSequence charSequence,
            int start, int end, int lineNum) {

        int oldColor = paint.getColor();


        paint.setColor(colorCircle);
        canvas.drawCircle(right - ((left + right) / 4), bottom + radius, radius, paint);
        paint.setColor(oldColor);

        Paint paintStroke = new Paint();
        paintStroke.setColor(colorStroke);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setStrokeWidth(radius / 4);
        canvas.drawCircle(right - ((left + right) / 4), bottom + radius, radius, paintStroke);
    }
}
