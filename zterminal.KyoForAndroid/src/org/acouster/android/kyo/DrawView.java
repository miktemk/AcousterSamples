package org.acouster.android.kyo;
//package acouster.andy2;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.awt.*;
//import java.lang.reflect.Constructor;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.Path.Direction;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnTouchListener;
//import android.view.animation.AccelerateDecelerateInterpolator;
//import android.view.animation.Animation;
//import android.view.animation.RotateAnimation;
//
//public class DrawView extends View implements OnTouchListener {
//    private static final String TAG = "DrawView";
//    private static final String QUOTE = "Nobody uses Java anymore. It's this big heavyweight ball and chain.";
//
//    List<Point> points = new ArrayList<Point>();
//    Paint paint = new Paint();
//
//    public DrawView(Context context) {
//        super(context);
//        setFocusable(true);
//        setFocusableInTouchMode(true);
//
//        this.setOnTouchListener(this);
//
//        paint.setColor(Color.WHITE);
//        paint.setAntiAlias(true);
//        
//        jobs = BitmapFactory.decodeResource(getResources(), R.drawable.death1);
//		jobsXOffset = jobs.getWidth() / 2;
//		jobsYOffset = jobs.getHeight() / 2;
//
//    }
//
//    @Override
//    public void onDraw(Canvas canvas) {
//    	super.onDraw(canvas);
//
//        // creates the animation the first time
//        if (anim == null) {
//            createAnim(canvas);
//        }
//
//        Path circle = new Path();
//
//        int centerX = canvas.getWidth() / 2;
//        int centerY = canvas.getHeight() / 2;
//        int r = Math.min(centerX, centerY);
//        
//        canvas.drawBitmap(jobs, centerX - jobsXOffset, centerY - jobsYOffset, null);
//
//        circle.addCircle(centerX, centerY, r, Direction.CW);
//        Paint paint = new Paint();
//        paint.setColor(Color.BLUE);
//        paint.setTextSize(30);
//        paint.setAntiAlias(true);
//
//        canvas.drawTextOnPath(QUOTE, circle, 0, 30, paint);
//
//        for (Point point : points) {
//            canvas.drawCircle(point.x, point.y, 5, paint);
//            // Log.d(TAG, "Painting: "+point);
//        }
//    }
//
//    public boolean onTouch(View view, MotionEvent event) {
//        // if(event.getAction() != MotionEvent.ACTION_DOWN)
//        // return super.onTouchEvent(event);
//        Point point = new Point();
//        point.x = event.getX();
//        point.y = event.getY();
//        points.add(point);
//        invalidate();
//        Log.d(TAG, "point: " + point);
//        return true;
//    }
//    
//    private Animation anim;
//    private Bitmap jobs;
//    private int jobsXOffset;
//    private int jobsYOffset;
//    private void createAnim(Canvas canvas) {
//        anim = new RotateAnimation(0, 360, canvas.getWidth() / 2, canvas
//                .getHeight() / 2);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(Animation.INFINITE);
//        anim.setDuration(10000L);
//        anim.setInterpolator(new AccelerateDecelerateInterpolator());
//
//        startAnimation(anim);
//    }
//}
//
//class Point {
//    float x, y;
//
//    @Override
//    public String toString() {
//        return x + ", " + y;
//    }
//}