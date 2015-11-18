package com.example.sehyeon.aimstack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by sehyeon on 2015-11-16.
 */
public class ProgressCircle extends View {

    private Paint circlePaint;
    private Paint progressPaint;

    private RectF backgroundCircle;
    private RectF progressArc;

    private float centerX;
    private float centerY;
    private float radious;

    private int iCurStep = 0;
    private int totalSec;
    private int doingSec;

    private boolean isMini=false;

    public ProgressCircle(Context context) {
        super(context);
        init();
    }

    public ProgressCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(isMini==false)
            drawMoving(canvas);
        else
            drawMiniCircle(canvas);

    }

    private void init() {

        circlePaint = new Paint() {
            {
                setDither(true);
                setStyle(Paint.Style.STROKE);
                setStrokeCap(Cap.BUTT);
                setStrokeJoin(Join.BEVEL);
                setColor(Color.parseColor("#FFdbdbdb"));
                setStrokeWidth(30);
                setAntiAlias(true);
            }
        };
        progressPaint = new Paint() {
            {
                setDither(true);
                setStyle(Paint.Style.STROKE);
                setStrokeCap(Cap.BUTT);
                setStrokeJoin(Join.BEVEL);
                setColor(Color.BLACK);
                setStrokeWidth(30);
                setAntiAlias(true);
            }

        };

        backgroundCircle = new RectF();
        progressArc = new RectF();

    }

    public void setTotalSec(int totalSec) {
        this.totalSec = totalSec;
        //  Log.d("progressCircle", "totalSec : "+totalSec);
    }

    public void setDoingSec(int doingSec) {
        this.doingSec = doingSec;
        // Log.d("progressCircle", "doingSec : "+doingSec);
    }

    private int getPercent() {
        // Log.d("progressCircle", "percent : "+(doingSec*100/totalSec));
        return (doingSec * 100 / totalSec);
    }

    public void drawMiniCircle(Canvas canvas){

        progressPaint.setStrokeWidth(10);
        circlePaint.setStrokeWidth(10);

        centerX = 100;
        centerY = 500;

        radious = 100;

        backgroundCircle.set(centerX - radious, centerY - radious, centerX + radious, centerY + radious);
        progressArc.set(centerX - radious, centerY - radious, centerX + radious, centerY + radious);

        canvas.drawArc(backgroundCircle, 270, 360, false, circlePaint);
        canvas.drawArc(progressArc, 270, -(360 * (getPercent() / 100f)), false, progressPaint);
    }
    public void drawMoving(Canvas canvas) {

        centerX = canvas.getWidth() / 2;
        centerY = canvas.getHeight() / 2;

        radious = (canvas.getWidth()-200)/2;
        backgroundCircle.set(centerX - radious, centerY - radious, centerX + radious, centerY + radious);
        progressArc.set(centerX - radious, centerY - radious, centerX + radious, centerY + radious);


        canvas.drawArc(backgroundCircle, 270, 360, false, circlePaint);
        if (iCurStep <= getPercent()) {
            canvas.drawArc(progressArc, 270, -(360 * (iCurStep / 100f)), false, progressPaint);
            iCurStep++;
            Log.d("ProgressCircle", "Percent : " + iCurStep);
        } else {
            iCurStep = 0;
        }

    }

    public void isMini(boolean mini){
        this.isMini=mini;
    }

}
