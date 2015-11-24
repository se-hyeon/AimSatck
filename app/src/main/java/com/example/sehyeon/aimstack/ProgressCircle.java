package com.example.sehyeon.aimstack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
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

import java.util.Calendar;

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
    private float radius;

    private int iCurStep = 0;
    private int totalSec;
    private int doingSec;

    private int WIDTH;
    private int HEIGHT;

    private Path heartPath;
    private Paint heartBackgroundPaint;
    private Paint heartProgressPaint;

    private int top;
    private int left;

    private float dash;

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
        if (getPercent()<100)
            drawMoving(canvas);
        else
            drawHeart(canvas);
    }

    private void init() {
        circlePaint = new Paint() {
            {
                setDither(true);
                setStyle(Paint.Style.STROKE);
                setStrokeCap(Cap.BUTT);
                setStrokeJoin(Join.BEVEL);
                setColor(Color.parseColor("#FF8ABCE2"));
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
                setColor(Color.parseColor("#FF165BCA"));
                setStrokeWidth(30);
                setAntiAlias(true);
            }
        };
        backgroundCircle = new RectF();
        progressArc = new RectF();

        heartPath = new Path();
        heartBackgroundPaint = new Paint() {
            {
                setDither(true);
                setStyle(Paint.Style.STROKE);
                setStrokeCap(Cap.BUTT);
                setStrokeJoin(Join.BEVEL);
                setColor(Color.parseColor("#FFECC0EB"));
                setStrokeWidth(30);
                setAntiAlias(true);
            }
        };

        heartProgressPaint = new Paint() {
            {
                setDither(true);
                setStyle(Paint.Style.STROKE);
                setStrokeCap(Cap.BUTT);
                setStrokeJoin(Join.BEVEL);
                setColor(Color.parseColor("#FFFC38E2"));
                setStrokeWidth(30);
                setAntiAlias(true);
            }
        };
        dash = 0;
    iCurStep=0;
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
        Log.d("progressCircle", "totalSec : "+totalSec);
        return (doingSec * 100 / totalSec);
    }


    public void drawMoving(Canvas canvas) {

        centerX = canvas.getWidth() / 2;
        centerY = canvas.getHeight() / 2;

        radius = (canvas.getWidth() - 200) / 2;
        backgroundCircle.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        progressArc.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);


        canvas.drawArc(backgroundCircle, 270, 360, false, circlePaint);
        if (iCurStep <= getPercent()) {
            canvas.drawArc(progressArc, 270, -(360 * (iCurStep / 100f)), false, progressPaint);
            iCurStep++;
            //   Log.d("ProgressCircle", "Percent : " + iCurStep);
        } else {
            iCurStep = 0;
        }

    }


    public void drawHeart(Canvas canvas) {

        centerX = canvas.getWidth() / 2;
        centerY = canvas.getHeight() / 2;

        WIDTH = canvas.getWidth() * 2;
        HEIGHT = canvas.getHeight() * 2;

        left = (int) centerX - (canvas.getWidth());
        top = (int) centerY - (canvas.getWidth() - 200);

        heartPath.moveTo(left + WIDTH / 2, top + HEIGHT / 4);

        heartPath.cubicTo(left + WIDTH / 5, top,
                left + WIDTH / 4, top + 3 * HEIGHT / 5,
                left + WIDTH / 2, top + 3 * HEIGHT / 5);

        heartPath.cubicTo(left + 3 * WIDTH / 4, top + 3 * HEIGHT / 5,
                left + 4 * WIDTH / 5, top,
                left + WIDTH / 2, top + HEIGHT / 4);

        canvas.drawPath(heartPath, heartBackgroundPaint);


        // 참고 : http://stackoverflow.com/questions/12769431/animating-the-drawing-of-a-canvas-path-on-android
        PathMeasure pm = new PathMeasure(heartPath, false);
        float fSegmentLen = pm.getLength() / 100;

        float[] dashes = {0.0f, Float.MAX_VALUE};
        dashes[0] = dash;

        if (dash <= pm.getLength()) {
            heartProgressPaint.setPathEffect(new DashPathEffect(dashes, 0));
            canvas.drawPath(heartPath, heartProgressPaint);

            dash += fSegmentLen;
        } else
            dash = 0;
    }

}
